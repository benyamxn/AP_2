package model;
public class Request {
    private RequestType requestType;
    private String parameter;
    private Point point;
    private int count;
    private VehicleType vehicleType;

    public Request(RequestType requestType) {
        this.requestType = requestType;
    }

    public Request(RequestType requestType, String parameter) {
        this.requestType = requestType;
        this.parameter = parameter;
    }

    public Request(RequestType requestType, int pointX, int pointY) {
        this.requestType = requestType;
        this.point = new Point(pointX, pointY);
    }

    public Request(RequestType requestType, int count) {
        this.requestType = requestType;
        this.count = count;
    }

    public Request(RequestType requestType, VehicleType vehicleType) {
        this.requestType = requestType;
        this.vehicleType = vehicleType;
    }

    public Request(RequestType requestType, String parameter, int count, VehicleType vehicleType) {
        this.requestType = requestType;
        this.parameter = parameter;
        this.count = count;
        this.vehicleType = vehicleType;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public String getParameter() {
        return parameter;
    }

    public Point getPoint() {
        return point;
    }

    public int getCount() {
        return count;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }
}
