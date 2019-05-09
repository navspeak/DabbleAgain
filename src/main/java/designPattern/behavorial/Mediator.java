package designPattern.behavorial;

import java.util.ArrayList;
import java.util.List;

class MediatorDemo {
    public static void main(String[] args) {

            Mediator mediator = new Mediator();

            LightObj bedroomLight = new LightObj("Bedroom");
            LightObj kitchenLight = new LightObj("Kitchen");

            mediator.registerLight(bedroomLight);
            mediator.registerLight(kitchenLight);

            Command turnOnAllLightsCommand = new TurnOnAllLightsCommand(mediator);

            turnOnAllLightsCommand.execute();

            Command turnOffAllLightsCommand = new TurnOffAllLightsCommand(mediator);

            turnOffAllLightsCommand.execute();
        }

}

//receiver
class LightObj {

    private boolean isOn = false;

    private String location = "";

    public LightObj() {

    }

    public LightObj(String location) {
        this.location = location;
    }

    public boolean isOn() {
        return isOn;
    }

    public void toggle() {
        if(isOn) {
            off();
            isOn = false;
        }
        else {
            on();
            isOn = true;
        }
    }

    public void on() {
        System.out.println(location + " Light switched on.");
    }

    public void off() {
        System.out.println(location + " Light switched off.");
    }
}


//colleague
interface Command {
    void execute();
}

//concrete command
class TurnOnAllLightsCommand implements Command {

    private Mediator med;

    public TurnOnAllLightsCommand(Mediator med) {
        this.med = med;
    }

    @Override
    public void execute() {
        med.turnOnAllLights();
    }
}

//concrete command
class TurnOffAllLightsCommand implements Command {

    private Mediator med;

    public TurnOffAllLightsCommand(Mediator med) {
        this.med = med;
    }

    @Override
    public void execute() {
        med.turnOffAllLights();
    }
}

class Mediator {

    private List<LightObj> lights = new ArrayList<>();

    public void registerLight(LightObj light) {
        lights.add(light);
    }

    public void turnOnAllLights() {
        for (LightObj light : lights) {
            if(!light.isOn()) {
                light.toggle();
            }
        }
    }

    public void turnOffAllLights() {
        for (LightObj light : lights) {
            if(light.isOn()) {
                light.toggle();
            }
        }
    }

}
