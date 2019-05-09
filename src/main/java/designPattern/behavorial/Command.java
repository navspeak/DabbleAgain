package designPattern.behavorial;

import java.util.ArrayList;
import java.util.List;

public class Command {
    public static void main(String[] args) {
        // Runnable is an example

        // object per receiver
        // decouples senders from processor
        Light bedroomLight = new Light(); // receivers
        Light kitchenLight = new Light();
        Switch lightSwitch = new Switch(); // Invoker

        ICommand toggleCommand = new ToggleCommand(bedroomLight);

        lightSwitch.storeAndExecute(toggleCommand);
        //lightSwitch.storeAndExecute(toggleCommand);
        //lightSwitch.storeAndExecute(toggleCommand);

        List<Light> lights = new ArrayList<>();
        lights.add(kitchenLight);
        lights.add(bedroomLight);
        ICommand allLightsCommand = new AllLightsCommand(lights);

        lightSwitch.storeAndExecute(allLightsCommand);
    }
}

//command
interface ICommand {
    void execute();
}

//receiver
class Light {
    private boolean isOn = false;
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
        System.out.println("Light switched on.");
    }
    public void off() {
        System.out.println("Light switched off.");
    }
}

//invoker
class Switch {
    public void storeAndExecute(ICommand command) {
        command.execute();
    }
}

//concrete command
class OnCommand implements ICommand {

    private Light light;

    public OnCommand(Light light) {
        this.light = light;
    }
    @Override
    public void execute() {
        light.on();
    }

}

class AllLightsCommand implements ICommand {
    private List<Light> lights;
    public AllLightsCommand(List<Light> lights) {
        this.lights = lights;
    }
    @Override
    public void execute() {
        for (Light light : lights) {
            if(light.isOn()) {
                light.toggle();
            }
        }
    }

}

class ToggleCommand implements ICommand {

    private Light light;

    public ToggleCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.toggle();
    }
}

