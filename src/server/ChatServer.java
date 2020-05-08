package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

// public class ChatServer {
//     private int port;
//     private Set<String> userNames = new HashSet<>();
//     private Set<UserThread> userThreads = new HashSet<>();

//     public ChatServer(int Port) {
//         this.port=Port;
//     }

//     public void execute() {
//         try(ServerSocket serverSocket = new ServerSocket(port)) {
            
//             System.out.println("Chat Server is listening on port " + port);

//             while (true) {
//                 Socket socket = serverSocket.accept();
//                 System.out.println("New user connected");
//                 UserThread newUser = new UserThread(socket, this);
//                 userThreads.add(newUser);
//                 newUser.start();

//             }
//         } catch (IOException e) {
//             // TODO Auto-generated catch block
//             System.out.println("Error in the server: " + e.getMessage());
//             e.printStackTrace();
//         }
//     }

//     public static void main(String[] args) {
//         if (args.length < 1) {
//             System.out.println("Syntax: java ChatServer <port-number>");
//             System.exit(0);
//             int port = Integer.parseInt(args[0]);

//             ChatServer server = new ChatServer(port);

//             server.execute();
//         }
//     }

//     public void broadcast(String message, UserThread excludeUser) {
//         for (UserThread aUser : userThreads) {
//             if (aUser != excludeUser) {
//                 aUser.sendMessage(message);
//             }
//         }

//     }

//     public void addUserName(String userName) {
//         userNames.add(userName);
//     }

//     public void removeUser(String userName,UserThread aUser){
//        boolean removed= userNames.remove(userName);
//        if (removed) {
//            userThreads.remove(aUser);
//            System.out.println("The user:"+ userName +"quitted");


//        }
//     }
//     // Set<String> getUserNames() {
// 	// 	return this.userNames;
//     // }
//     /**
//      * @return the userNames
//      */
//     public Set<String> getUserNames() {
//         return userNames;
//     }
//     public boolean hasUsers(){
//         return !this.userNames.isEmpty();
//     }
// }
public class ChatServer {
	private int port;
	private Set<String> userNames = new HashSet<>();
	private Set<UserThread> userThreads = new HashSet<>();

	public ChatServer(int port) { 
		this.port = port;
	}

	public void execute() {
		try (ServerSocket serverSocket = new ServerSocket(port)) {

			System.out.println("Chat Server is listening on port " + port);

			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("New user connected");

				UserThread newUser = new UserThread(socket, this);
				userThreads.add(newUser);
				newUser.start();

			}

		} catch (IOException ex) {
			System.out.println("Error in the server: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Syntax: java ChatServer <port-number>");
			System.exit(0);
		}

		int port = Integer.parseInt(args[0]);

		ChatServer server = new ChatServer(port);
		server.execute();
	}

	/**
	 * Delivers a message from one user to others (broadcasting)
	 */
    // Cung cấp một tin nhắn từ người dùng này đến người dùng khác (phát sóng)
	public void broadcast(String message, UserThread excludeUser) {
		for (UserThread aUser : userThreads) {
			if (aUser != excludeUser) {
				aUser.sendMessage(message);
			}
		}
	}

	/**
	 * Stores username of the newly connected client.
	 */
    // Lưu tên người dùng của khách hàng mới được kết nối.
	 public void addUserName(String userName) {
		userNames.add(userName);
	}

	/**
	 * When a client is disconneted, removes the associated username and UserThread
	 */
    // Khi một máy khách bị từ chối, hãy xóa tên người dùng và UserThread được liên kết
	 public void removeUser(String userName, UserThread aUser) {
		boolean removed = userNames.remove(userName);
		if (removed) {
			userThreads.remove(aUser);
			System.out.println("The user " + userName + " quitted");
		}
	}

	 /**
      * @return the userNames
      */
     public Set<String> getUserNames() {
         return userNames;
     }

	/**
	 * Returns true if there are other users connected (not count the currently connected user)
	 */
    // Trả về true nếu có người dùng khác được kết nối (không tính người dùng hiện đang kết nối)
	public boolean hasUsers() {
		return !this.userNames.isEmpty();
	}
}