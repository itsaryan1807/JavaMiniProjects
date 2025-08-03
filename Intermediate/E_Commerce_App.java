package Java_Programs.Projects_Boost;

import java.util.*;

public class E_Commerce_App {

    static Scanner sc = new Scanner(System.in);
    static List<Product> products = new ArrayList<>();
    static List<User> users = new ArrayList<>();
    static List<CartItem> cart = new ArrayList<>();
    static User currentUser = null;

                    // ----- Data Models ------

    static class Product {
        int id;
        String name;
        String category;
        double price;
        int stock;

        Product(int id, String name, String category, double price, int stock) {
            this.id = id;
            this.name = name;
            this.category = category;
            this.price = price;
            this.stock = stock;
        }

        public String toString() {
            return String.format("[%d] %s (%s) - ₹%.2f | Stock: %d", id, name, category, price, stock);
        }
    }

    static class User {
        String username;
        String password;
        String role; // "user" or "admin"
        List<Order> orders = new ArrayList<>();

        User(String username, String password, String role) {
            this.username = username;
            this.password = password;
            this.role = role;
        }
    }

    static class CartItem {
        Product product;
        int quantity;

        CartItem(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }

        public String toString() {
            return product.name + " x " + quantity + " = ₹" + (quantity * product.price);
        }
    }

    static class Order {
        String orderId;
        List<CartItem> items;
        double totalAmount;
        Date date;

        Order(String orderId, List<CartItem> items, double totalAmount) {
            this.orderId = orderId;
            this.items = items;
            this.totalAmount = totalAmount;
            this.date = new Date();
        }

        public String toString() {
            return "Order #" + orderId + " | ₹" + totalAmount + " | " + date;
        }
    }


    public static void main(String[] args) {
        // Dummy data
        users.add(new User("admin", "admin123", "admin"));
        products.add(new Product(1, "Java Book", "Books", 500.0, 10));
        products.add(new Product(2, "Mouse", "Electronics", 799.0, 15));
        products.add(new Product(3, "Water Bottle", "Kitchen", 150.0, 20));

        while (true) {
            System.out.println("\n🌐 Welcome to Smart E-Commerce App");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option : ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    registerUser();
                    break;
                case "2":
                    loginUser();
                    break;
                case "3":
                    System.out.println("👋 Exiting SmartECommerceApp. Goodbye!");
                    return;
                default:
                    System.out.println("❌ Invalid input.");
                    break;
            }
        }
    }

    static void registerUser() {
        System.out.print("Choose username: ");
        String uname = sc.nextLine();
        System.out.print("Choose password: ");
        String pass = sc.nextLine();

        for (User u : users) {
            if (u.username.equals(uname)) {
                System.out.println("❌ Username already exists!");
                return;
            }
        }

        users.add(new User(uname, pass, "user"));
        System.out.println("✅ Registered successfully!");
    }

    static void loginUser() {
        System.out.print("Username: ");
        String uname = sc.nextLine();
        System.out.print("Password: ");
        String pass = sc.nextLine();

        currentUser = null;
        for (User u : users) {
            if (u.username.equals(uname) && u.password.equals(pass)) {
                currentUser = u;
                break;
            }
        }

        if (currentUser == null) {
            System.out.println("❌ Invalid login.");
            return;
        }

        System.out.println("✅ Login successful. Hello, " + currentUser.username + "!");

        if (currentUser.role.equals("admin")) {
            adminPanel();
        } else {
            cart.clear();
            userDashboard();
        }
    }

    static void adminPanel() {
        boolean adminPanel = true;
        while (adminPanel) {
            System.out.println("\n🔐 Admin Panel");
            System.out.println("1. View Products");
            System.out.println("2. Add Product");
            System.out.println("3. Edit Product Stock");
            System.out.println("4. Delete Product");
            System.out.println("5. View Stats");
            System.out.println("6. Logout");
            System.out.print("Choose: ");
            String adminChoice = sc.nextLine();

            switch (adminChoice) {
                case "1":
                    viewProducts();
                    break;
                case "2":
                    addProduct();
                    break;
                case "3":
                    editProductStock();
                    break;
                case "4":
                    deleteProduct();
                    break;
                case "5":
                    viewStats();
                    break;
                case "6":
                    currentUser = null;
                    adminPanel = false;
                    break;
                default:
                    System.out.println("❌ Invalid choice.");
                    break;
            }
        }
    }

    static void viewProducts() {
        if (products.isEmpty()) {
            System.out.println("🚫 No products available.");
            return;
        }
        for (Product p : products) {
            System.out.println(p);
        }
    }

    static void addProduct() {
        System.out.print("Product name: ");
        String name = sc.nextLine();
        System.out.print("Category: ");
        String cat = sc.nextLine();
        System.out.print("Price: ");
        double price = Double.parseDouble(sc.nextLine());
        System.out.print("Stock: ");
        int stock = Integer.parseInt(sc.nextLine());
        int newId = products.size() + 1;

        products.add(new Product(newId, name, cat, price, stock));
        System.out.println("✅ Product added.");
    }

    static void editProductStock() {
        viewProducts();
        System.out.print("Enter Product ID to update stock: ");
        int id = Integer.parseInt(sc.nextLine());

        for (Product p : products) {
            if (p.id == id) {
                System.out.print("Enter new stock: ");
                p.stock = Integer.parseInt(sc.nextLine());
                System.out.println("✅ Stock updated.");
                return;
            }
        }

        System.out.println("❌ Product not found.");
    }

    static void deleteProduct() {
        System.out.print("Enter Product ID to delete: ");
        int delId = Integer.parseInt(sc.nextLine());
        boolean removed = products.removeIf(p -> p.id == delId);

        if (removed) {
            System.out.println("✅ Product deleted.");
        } else {
            System.out.println("❌ Product not found.");
        }
    }

    static void viewStats() {
        System.out.println("🧮 Total Users: " + users.size());
        System.out.println("📦 Total Products: " + products.size());
        long totalOrders = users.stream().mapToLong(u -> u.orders.size()).sum();
        System.out.println("🧾 Total Orders: " + totalOrders);
    }

    static void userDashboard() {
        boolean userPanel = true;
        while (userPanel) {
            System.out.println("\n🧑 User Dashboard");
            System.out.println("1. View Products");
            System.out.println("2. Search Product");
            System.out.println("3. Add to Cart");
            System.out.println("4. View Cart");
            System.out.println("5. Checkout");
            System.out.println("6. View Order History");
            System.out.println("7. Logout");
            System.out.print("Choose: ");
            String userChoice = sc.nextLine();

            switch (userChoice) {
                case "1":
                    viewProducts();
                    break;
                case "2":
                    searchProduct();
                    break;
                case "3":
                    addToCart();
                    break;
                case "4":
                    viewCart();
                    break;
                case "5":
                    checkout();
                    break;
                case "6":
                    viewOrderHistory();
                    break;
                case "7":
                    currentUser = null;
                    userPanel = false;
                    break;
                default:
                    System.out.println("❌ Invalid choice.");
                    break;
            }
        }
    }

    static void searchProduct() {
        System.out.print("Enter product name or category: ");
        String query = sc.nextLine().toLowerCase();

        boolean found = false;
        for (Product p : products) {
            if (p.name.toLowerCase().contains(query) || p.category.toLowerCase().contains(query)) {
                System.out.println(p);
                found = true;
            }
        }

        if (!found) System.out.println("❌ No matching product found.");
    }

    static void addToCart() {
        viewProducts();
        System.out.print("Enter Product ID to add to cart: ");
        int pid = Integer.parseInt(sc.nextLine());
        Product selected = null;

        for (Product p : products) {
            if (p.id == pid) {
                selected = p;
                break;
            }
        }

        if (selected == null) {
            System.out.println("❌ Product not found.");
            return;
        }

        System.out.print("Enter quantity: ");
        int qty = Integer.parseInt(sc.nextLine());

        if (qty > selected.stock) {
            System.out.println("❌ Not enough stock.");
            return;
        }

        cart.add(new CartItem(selected, qty));
        System.out.println("✅ Added to cart.");
    }

    static void viewCart() {
        if (cart.isEmpty()) {
            System.out.println("🛒 Your cart is empty.");
            return;
        }

        double total = 0;
        for (CartItem c : cart) {
            System.out.println(c);
            total += c.quantity * c.product.price;
        }

        System.out.printf("🧾 Total: ₹%.2f\n", total);
    }

    static void checkout() {
        if (cart.isEmpty()) {
            System.out.println("🛒 Your cart is empty.");
            return;
        }

        double total = 0;
        for (CartItem c : cart) {
            if (c.quantity > c.product.stock) {
                System.out.println("❌ Not enough stock for " + c.product.name);
                return;
            }
            total += c.quantity * c.product.price;
        }

        // Deduct stock
        for (CartItem c : cart) {
            c.product.stock -= c.quantity;
        }

        String orderId = "ORD" + new Random().nextInt(9999);
        currentUser.orders.add(new Order(orderId, new ArrayList<>(cart), total));
        System.out.printf("✅ Checkout complete. Order ID: %s | Amount: ₹%.2f\n", orderId, total);
        cart.clear();
    }

    static void viewOrderHistory() {
        if (currentUser.orders.isEmpty()) {
            System.out.println("📭 No order history found.");
            return;
        }

        for (Order o : currentUser.orders) {
            System.out.println("\n" + o);
            for (CartItem item : o.items) {
                System.out.println("   - " + item);
            }
        }
    }

}
