package com.electronic.facture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.electronic.facture.models.AppRole;
import com.electronic.facture.models.Categorie;
import com.electronic.facture.models.Reference;
import com.electronic.facture.models.Utilisateur;
import com.electronic.facture.repositories.CategorieRepo;
import com.electronic.facture.repositories.ReferenceRepo;
import com.electronic.facture.services.AccountServiceImpl;
import com.electronic.facture.services.AppRoleService;
import com.electronic.facture.services.FactureService;
import com.electronic.facture.services.UtilisateurService;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@EnableScheduling
public class EFactureApplication {

	public static void main(String[] args) {
		SpringApplication.run(EFactureApplication.class, args);
	}
	
	@Autowired
	private FactureService factureService;
	
	@Scheduled(cron = "0 1 1 * * *")
    public void task() {
        this.factureService.echeance();
    }
	
	@Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
	
	@Bean
    CommandLineRunner start(AccountServiceImpl accountService, ReferenceRepo referenceRepo ,CategorieRepo categorieRepo ,AppRoleService appRoleService, UtilisateurService utilisateurService){
        return args -> {
            appRoleService.addNewRole(new AppRole("ADMIN"));
            appRoleService.addNewRole(new AppRole("USER"));
            categorieRepo.save(new Categorie("SERVICES", null));
            categorieRepo.save(new Categorie("PRODUITS", null));
            categorieRepo.save(new Categorie("PRODUITS & SERVICES", null));
            if(!referenceRepo.existsById((long)1)) {
            	referenceRepo.save(new Reference());
            }
            utilisateurService.save(new Utilisateur("admin", "admin"));
//            utilisateurService.addNewUser(new Utilisateur("aabidi", "hassan"));
            accountService.affectRoleToUser("admin", "ADMIN");
//            accountService.affectRoleToUser("aabidi", "USER");

        };
    }

}
