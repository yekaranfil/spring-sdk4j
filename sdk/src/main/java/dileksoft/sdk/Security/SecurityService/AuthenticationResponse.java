package dileksoft.sdk.Security.SecurityService;

public class AuthenticationResponse  {

    private Data data;
    private int status;
    private Object properties;

    // Getter ve Setter metotları
    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getProperties() {
        return properties;
    }

    public void setProperties(Object properties) {
        this.properties = properties;
    }

    // İç içe Data sınıfı
    public static class Data {
        private String token;
        private long expiresAt;

        // Getter ve Setter metotları
        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public long getExpiresAt() {
            return expiresAt;
        }

        public void setExpiresAt(long expiresAt) {
            this.expiresAt = expiresAt;
        }
    }
}
