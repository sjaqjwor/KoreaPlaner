package wooklee.koreaplaner.controllers.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserResponse {
    private Object data;
    private String msg;
    private Status status;

    public UserResponse(String msg,Status status){
        this.data=null;
        this.msg=msg;
        this.status=status;
    }
    public enum Status{
        NOCONTENT(204,"No Content") , OK(200,"OK"),DUFLICATE(409,"DUFLICATE"),
        NOTFOUND(400,"NotFoundUser");

        private int code;
        private String content;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        Status(int code, String content){
            this.code=code;
            this.content=content;
        }
    }
}
