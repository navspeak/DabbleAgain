package graph;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Data
public class Vertex {
    private int data;
    private boolean visited;
    private List<Vertex> adjList = new LinkedList<>();
    Vertex(int data) {this.data = data;}
}

class LombakTest {
    public static void main(String[] args) {
        Vertex v = new Vertex(1);


    }
}