package Java_Programs.Projects_CodeChef.Intermediate;

import java.net.InetSocketAddress;

public class AddressExample {
    public static void main(String[] args) {
        // Create an address for localhost on port 8000
        InetSocketAddress address = new InetSocketAddress("localhost", 8000);

        // Display the address details
        System.out.println("Host: " + address.getHostName());  // Output: localhost
        System.out.println("Port: " + address.getPort());      // Output: 8000
    }
}
