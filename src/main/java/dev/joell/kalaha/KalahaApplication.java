package dev.joell.kalaha;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import dev.joell.kalaha.board.Board;

@SpringBootApplication
public class KalahaApplication {

	public static void main(String[] args) {
		// SpringApplication.run(KalahaApplication.class, args);

        Board board = new Board(6, 4);
        System.out.println(board.asciiFormatString());
	}

}
