package designPattern.creational;

import com.google.common.base.Preconditions;

public class BuilderPattern {
    private int age;
    private String name;
    private String address;

    private BuilderPattern(int age, String name, String address) {
        this.age = age;
        this.name = name;
        this.address = address;
    }

    public static Builder startBuilding() {
        return new Builder();
    }

    public static class Builder {
        private int age;
        private String name;
        private String address;

        public Builder age(int age) { this.age = age; return this;}
        public Builder name(String name) { this.name = name; return this;}
        public Builder address(String address) { this.address = address; return this;}

        public BuilderPattern build() {
            Preconditions.checkArgument(age > 10 && name.length() > 5);
            return new BuilderPattern(age, name, address);
        }
    }
}

class BuilderDemo {
    public static void main(String[] args) {
        BuilderPattern builderPattern = BuilderPattern.startBuilding().
                name("Ram").age(26).address("Patna").build();
    }
}
