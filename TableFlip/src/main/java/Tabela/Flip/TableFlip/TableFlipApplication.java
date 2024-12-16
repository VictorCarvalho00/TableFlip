package Tabela.Flip.TableFlip;

import Tabela.Flip.TableFlip.Principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TableFlipApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TableFlipApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.menu();
	}
}
