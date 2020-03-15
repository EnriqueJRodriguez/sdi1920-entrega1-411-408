package com.uniovi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class PO_PrivateView extends PO_NavView {
	
	public static void search(WebDriver driver, String searchText) {
		WebElement searchForm = driver.findElement(By.name("searchText"));
		searchForm.click();
		searchForm.clear();
		searchForm.sendKeys(searchText);
		// Pulsar el boton de Enviar.
		By boton = By.name("searchBtn");
		driver.findElement(boton).click();
	}
	
	static public void logout(WebDriver driver) {
		PO_NavView.clickOption(driver, "logout", "class", "btn btn-primary");
	}
}
