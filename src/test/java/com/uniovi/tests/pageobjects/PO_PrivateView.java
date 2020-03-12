package com.uniovi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.uniovi.tests.util.SeleniumUtils;

public class PO_PrivateView extends PO_NavView {

	static public void fillFormAddMark(WebDriver driver, int userOrder, String descriptionp, String scorep) {
		// Esperamos 5 segundos a que carge el DOM porque en algunos equipos falla
		SeleniumUtils.esperarSegundos(driver, 5);
		// Seleccionamos el alumno userOrder
		new Select(driver.findElement(By.id("user"))).selectByIndex(userOrder);
		// Rellenamos el campo de descripción
		WebElement description = driver.findElement(By.name("description"));
		description.clear();
		description.sendKeys(descriptionp);
		WebElement score = driver.findElement(By.name("score"));
		score.click();
		score.clear();
		score.sendKeys(scorep);
		By boton = By.className("btn");
		driver.findElement(boton).click();
	}

	static public void fillFormCheckPrivateZone(WebDriver driver, String usernamep, String passwordp, String textp) {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, usernamep, passwordp);
		// Comprobamos que entramos en la pagina privada del usuario
		PO_View.checkElement(driver, "text", textp);
	}
	
	static public void logout(WebDriver driver, String textp) {
		clickOption(driver, "logout", "text", textp);
	}
}
