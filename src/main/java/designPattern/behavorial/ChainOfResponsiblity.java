package designPattern.behavorial;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChainOfResponsiblity {
    /*
-Decoupling of sender and receiver
-Receiver contains reference to next receiver
-Promotes loose coupling
-No Handler - OK
-Examples:
-java.util.logging.Logger#log()
-javax.servlet.Filter#doFilter()
-Spring Security Filter Chain
     */

    private static final Logger logger = Logger.getLogger(ChainOfResponsiblity.class.getName());

    public static void main(String args[]) {

        //level to log at
        logger.setLevel(Level.FINER);

        ConsoleHandler handler = new ConsoleHandler();
        //level to publish at
        handler.setLevel(Level.FINER);
        logger.addHandler(handler);

        logger.finest("Finest level of logging"); //this one won't print
        logger.finer("Finer level, but not as fine as finest");
        logger.fine("Fine, but not as fine as finer or finest");

        Director bryan = new Director();
        VP crystal = new VP();
        CEO jeff = new CEO();

        bryan.setSuccessor(crystal);
        crystal.setSuccessor(jeff);

        Request request = new Request(RequestType.CONFERENCE, 500);
        bryan.handleRequest(request);

        request = new Request(RequestType.PURCHASE, 1000);
        crystal.handleRequest(request);

        request = new Request(RequestType.PURCHASE, 2000);
        bryan.handleRequest(request);

    }
}

enum RequestType {
    CONFERENCE, PURCHASE;
}

class Request {

    private RequestType requestType;
    private double amount;

    public Request(RequestType requestType, double amount) {
        this.requestType = requestType;
        this.amount = amount;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public double getAmount() {
        return amount;
    }
}

abstract class Handler {

    protected Handler successor;

    public void setSuccessor(Handler successor) {
        this.successor = successor;
    }

    public abstract void handleRequest(Request request);

}



class Director extends Handler {

    @Override
    public void handleRequest(Request request) {
        if(request.getRequestType() == RequestType.CONFERENCE) {
            System.out.println("Directors can approve conferences");
        }
        else {
            successor.handleRequest(request);
        }
    }
}

class VP extends Handler {

    @Override
    public void handleRequest(Request request) {
        if(request.getRequestType() == RequestType.PURCHASE) {
            if(request.getAmount() < 1500) {
                System.out.println("VPs can approve purchases below 1500");
            }
            else {
                successor.handleRequest(request);
            }
        }
    }
}

class CEO extends Handler {

    @Override
    public void handleRequest(Request request) {

        System.out.println("CEOs can approve anything they want");
    }
}