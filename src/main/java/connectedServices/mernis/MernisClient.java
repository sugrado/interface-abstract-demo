package connectedServices.mernis;

import com.sugrado.utils.ConfigLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class MernisClient {
    Properties mernisConfig;

    public MernisClient() {
        mernisConfig = ConfigLoader.getConfig("mernis");
    }

    public boolean TCKimlikNoDogrula(String identityNumber, String firstName, String lastName, String birthYear) {
        String url = mernisConfig.getProperty("host") + mernisConfig.getProperty("endpoint");
        String requestBody = createXMLRequestBody(identityNumber, firstName, lastName, birthYear);
        String response = sendRequest(url, requestBody);
        return Boolean.parseBoolean(response);
    }

    private String sendRequest(String url, String requestBody) {
        String result;
        try {
            URL urlObj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
            con.setRequestProperty("Host", mernisConfig.getProperty("host"));
            con.setDoOutput(true);

            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(requestBody.getBytes(StandardCharsets.UTF_8));
                wr.flush();
            }

            if (con.getResponseCode() != 200)
                throw new RuntimeException("Mernis Servisine Bağlanılamadı"); // TODO: Loglanabilir

            StringBuilder response;
            try (InputStreamReader isr = new InputStreamReader(con.getInputStream());
                 BufferedReader br = new BufferedReader(isr)) {
                String inputLine;
                response = new StringBuilder();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
            }
            result = parseXmlResponse(response.toString());
        } catch (Exception ex) {
            throw new RuntimeException("Mernis Servisine Bağlanılamadı."); // TODO: Loglanabilir
        }
        return result;
    }

    private String createXMLRequestBody(String identityNumber, String firstName, String lastName, String birthYear) {
        StringBuilder requestBody = new StringBuilder();
        requestBody.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        requestBody.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
        requestBody.append("<soap:Body>");
        requestBody.append("<TCKimlikNoDogrula xmlns=\"http://tckimlik.nvi.gov.tr/WS\">");
        requestBody.append(String.format("<TCKimlikNo>%s</TCKimlikNo>", identityNumber));
        requestBody.append(String.format("<Ad>%s</Ad>", firstName));
        requestBody.append(String.format("<Soyad>%s</Soyad>", lastName));
        requestBody.append(String.format("<DogumYili>%s</DogumYili>", birthYear));
        requestBody.append("</TCKimlikNoDogrula>");
        requestBody.append("</soap:Body>");
        requestBody.append("</soap:Envelope>");
        return requestBody.toString();
    }

    private String parseXmlResponse(String xmlResponse) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        String textResult;
        try (var inputStream = new ByteArrayInputStream(xmlResponse.getBytes(StandardCharsets.UTF_8))) {
            Document doc = builder.parse(inputStream);
            NodeList nodeList = doc.getElementsByTagName("TCKimlikNoDogrulaResult");
            Element element = (Element) nodeList.item(0);
            textResult = element.getTextContent();
        }
        return textResult;
    }
}
