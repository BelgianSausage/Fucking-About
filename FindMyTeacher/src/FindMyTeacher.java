import java.util.Scanner;
public class FindMyTeacher {

	public static void main(String[] args) {
		Scanner kb = new Scanner (System.in);
		System.out.println("Try to guess where your teacher is! Enter a number between 1 and 10!");
		String roomStr = kb.nextLine();
		int room = Integer.parseInt(roomStr);
		if(room == 1) {
			System.out.println("Mr. Coetzee is in this room!");
		}
		else if(room == 6) {
			System.out.println("Mr. Taylor is in this room!");
		}
		else if(room == 8) {
			System.out.println("Mr. Lowe is in this room!");
		}
		else {
			System.out.println("No one is in this room...");
		}
	}

}
