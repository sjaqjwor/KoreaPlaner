package wooklee.koreaplaner.controllers.responses;

public class DefaultResponse {

    private Status status;
    private Object object;

    public enum Status{
        SUCCESS,FAIL
    }

    public DefaultResponse(Status status, Object object){
        this.status=status;
        this.object=object;
    }
    public DefaultResponse(Status status){
        this.status=status;
    }

    public Object getObject(){
        return this.object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
    public void setStatus(Status status){
        this.status=status;
    }

    public Status getStatus() {
        return this.status;
    }

    @Override
    public String toString(){
        return String.format("\t\tstatus : "+status+"\n \t\tobject : "+object.toString());
    }
}
