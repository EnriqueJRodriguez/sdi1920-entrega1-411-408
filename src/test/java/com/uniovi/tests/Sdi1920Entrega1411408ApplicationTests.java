package com.uniovi.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.util.Assert;
import com.uniovi.entities.User;
import com.uniovi.services.UsersService;
import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_NavView;
import com.uniovi.tests.pageobjects.PO_PrivateView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.tests.util.SeleniumUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Sdi1920Entrega1411408ApplicationTests {

	private UsersService userService;

	// Hugo

	// GNU/Linux
	static String PathFirefox = "/usr/bin/firefox";
	static String GeckDriver024 = "/home/asuka/Universidad/Tercero/SDI/Lab/SecondPart/Other_files/Materials/geckodriver";

	// Enrique

	// Windows
//	static String PathFirefox = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
//	static String GeckDriver024 = "C:\\Users\\EnriqueJRodriguez\\Downloads\\OneDrive_2020-03-02\\PL-SDI-Ses5-material\\geckodriver024win64.exe";

	// Same for all OS
	static WebDriver driver = getDriver(PathFirefox, GeckDriver024);
	static String URL = "http://localhost:8090";

	private static WebDriver getDriver(String pathFirefox2, String geckDriver0242) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		System.setProperty("webdriver.gecko.driver", GeckDriver024);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	@Before
	public void setUp() throws Exception {
		driver.navigate().to(URL);
	}

	@After
	public void tearDown() throws Exception {
		driver.manage().deleteAllCookies();
	}

	@AfterClass
	static public void end() {
		// Cerramos el navegador al finalizar las pruebas
		driver.quit();
	}

	// PR01. Registro de Usuario con datos válidos.
	@Test
	public void PR01() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "marcos@sdiuniovi.es", "Marcos", "Rupertez", "123456", "123456");
		// Comprobamos que entramos en la sección privada
		PO_View.checkKey(driver, "home.logas", PO_Properties.getSPANISH());
//		Assert.notNull(userService.getUserByEmail("marcos@sdiuniovi.es").getLastName().equals("Rupertez"),
//				"Usuario No Encontrado");
		PO_PrivateView.logout(driver);
	}

	// PR02. Registro de Usuario con datos inválidos (email vacío, nombre vacío,
	// apellidos vacíos)
	@Test
	public void PR02() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "", "", "", "123456", "123456");
		PO_RegisterView.checkKey(driver, "Error.empty", PO_Properties.getSPANISH());
	}

	// PR03. Registro de Usuario con datos inválidos (repetición de contraseña
	// inválida).
	@Test
	public void PR03() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "albeto@uniovi.es", "Alberto", "Monzon", "123456", "12");
		PO_RegisterView.checkKey(driver, "Error.signup.passwordConfirm.coincidence", PO_Properties.getSPANISH());
//			Assert.isNull(userService.getUserByEmail("albeto@uniovi.es"),"Usuario No Encontrado");
	}

	// PR04. Registro de Usuario con datos inválidos (email existente).
	@Test
	public void PR04() {
		String repeatedEmail = "marcos@sdiuniovi.es"; // Usamos el del test anterior por defecto
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, repeatedEmail, "Alberto", "Monzon", "123456", "12");
		PO_RegisterView.checkKey(driver, "Error.signup.email.duplicate", PO_Properties.getSPANISH());
//			Assert.isTrue(!userService.getUserByEmail(repeatedEmail).getLastName().equals("Monzon"), "El usuario repetido no se inserto" );
	}

	// PR05. Inicio de sesión con datos válidos (administrador).
	@Test
	public void PR05() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "juansdi@uniovi.es", "123456");
		// Comprobamos que entramos en la pagina privada de Alumno
		PO_NavView.checkElement(driver, "text",
				PO_NavView.getP().getString("nav.message.users", PO_Properties.getSPANISH()));
		PO_PrivateView.logout(driver);
	}

	// PR06. Inicio de sesión con datos válidos (usuario estándar).
	@Test
	public void PR06() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "jaimitosdi@uniovi.es", "123456");
		// Comprobamos que entramos en la pagina privada de Alumno
		PO_NavView.checkElement(driver, "text",
				PO_NavView.getP().getString("nav.message.users", PO_Properties.getSPANISH()));
		PO_PrivateView.logout(driver);
	}

	// PR07. Inicio de sesión con datos inválidos (usuario estándar, campo email y
	// contraseña vacíos).
	@Test
	public void PR07() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "", "");
		// Comprobamos que entramos en la pagina privada de Alumno
		PO_LoginView.checkKey(driver, "login.title", PO_Properties.getSPANISH());
	}

	// PR08. Inicio de sesión con datos válidos (usuario estándar, email existente,
	// pero contraseña incorrecta).
	@Test
	public void PR08() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "jaimitosdi@uniovi.es", "12");
		// Comprobamos que entramos en la pagina privada de Alumno
		PO_LoginView.checkKey(driver, "login.title", PO_Properties.getSPANISH());
	}

	// PR15. Desde el listado de usuarios de la aplicación,enviar una invitación de
	// amistad a un usuario.
	@Test
	public void PR15() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "pepitosdi@uniovi.es", "123456");
		// Comprobamos que entramos en la pagina privada del usuario
		PO_NavView.checkElement(driver, "text",
				PO_NavView.getP().getString("nav.message.users", PO_Properties.getSPANISH()));
		// Mandamos una invitatión al usuario jaimito
		List<WebElement> botones = PO_View.checkElement(driver, "free",
				"//td[contains(text(), 'jaimitosdi@uniovi.es')]/following-sibling::*/btn[contains(@href, '/user/{id}/invitation/send')]");
		botones.get(0).click();
		// Nos desconectamos
		PO_PrivateView.logout(driver);
		// Nos conectamos como jaimito
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "jaimitosdi@uniovi.es", "123456");
		// Pinchamos en la opción de menú de invitaciones
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'invitations-menu')]/a");
		elementos.get(0).click();
		// Pinchamos en la opción de lista de invitaciones.
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, '/invitation/list')]");
		elementos.get(0).click();
		// Comprobamos que tenemos una invitación de amistad
		PO_NavView.checkElement(driver, "text", "pepitosdi@uniovi.es");
	}

	// PR16. Desde el listado de usuarios de la aplicación,enviar una invitación de
	// amistad a un usuario al que ya le habíamos enviado la invitación previamente
	@Test
	public void PR16() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "pepitosdi@uniovi.es", "123456");
		// Comprobamos que entramos en la pagina privada del usuario
		PO_NavView.checkElement(driver, "text",
				PO_NavView.getP().getString("nav.message.users", PO_Properties.getSPANISH()));
		// Mandamos una invitatión a la usuario Max
		List<WebElement> botones = PO_View.checkElement(driver, "free",
				"//td[contains(text(), 'max@arcadia.com')]/following-sibling::*/button[contains(@href, '/user/{id}/invitation/send')]");
		// Comprobamos que el botón no se muestra ya que existe una invitación
		// entre éstos dos usuarios
		assertEquals(true, botones.isEmpty());
	}

	// PR17. Mostrar el listado de invitaciones de amistad recibidas
	@Test
	public void PR17() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "pepitosdi@uniovi.es", "123456");
		// Comprobamos que entramos en la pagina privada del usuario
		PO_NavView.checkElement(driver, "text",
				PO_NavView.getP().getString("nav.message.users", PO_Properties.getSPANISH()));
		// Pinchamos en la opción de menú de invitaciones
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'invitations-menu')]/a");
		elementos.get(0).click();
		// Pinchamos en la opción de lista de invitaciones.
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, '/invitation/list')]");
		elementos.get(0).click();
		// Contamos las invitaciones (Debería haber una de Max y otra de Laura)
		elementos = PO_View.checkElement(driver, "free", "//td/div");
		assertEquals(2, elementos.size());
	}

	// PR18. Sobre el listado de invitaciones recibidas. Hacer click en el
	// botón/enlace de una de ellas y comprobar que dicha solicitud desaparece del
	// listado de invitaciones.
	@Test
	public void PR18() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "pepitosdi@uniovi.es", "123456");
		// Comprobamos que entramos en la pagina privada del usuario
		PO_NavView.checkElement(driver, "text",
				PO_NavView.getP().getString("nav.message.users", PO_Properties.getSPANISH()));
		// Pinchamos en la opción de menú de invitaciones
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'invitations-menu')]/a");
		elementos.get(0).click();
		// Pinchamos en la opción de lista de invitaciones.
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, '/invitation/list')]");
		elementos.get(0).click();
		// Aceptamos una de las dos invitaciones y comprobamos que solo queda una
		elementos = PO_View.checkElement(driver, "free", "//td/div/button");
		assertEquals(2, elementos.size());
		elementos.get(0).click();
		assertEquals(1, elementos.size());
	}

	// PR19. Mostrar el listadode amigos de unusuario. Comprobar que el listado
	// contiene los amigos que deben ser.
	@Test
	public void PR19() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "max@arcadia.com", "123456");
		// Comprobamos que entramos en la pagina privada del usuario
		PO_NavView.checkElement(driver, "text",
				PO_NavView.getP().getString("nav.message.users", PO_Properties.getSPANISH()));
		// Pinchamos en la opción de menú de amigos
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'friends-menu')]/a");
		elementos.get(0).click();
		// Pinchamos en la opción de lista de amigos.
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, '/friend/list')]");
		elementos.get(0).click();
		// Miramos que tenemos un amigo
		elementos = PO_View.checkElement(driver, "free", "//tr[contains(text(), 'jaimitosdi@uniovi.es')]");
		assertEquals(1, elementos.size());
	}

//	// PR06. Prueba del formulario de registro. DNI repetido en la BD, nombre corto,
//	// ...
//	@Test
//	public void PR06() {
//		// Vamos al formulario de registro
//		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
//		// Rellenamos el formulario.
//		PO_RegisterView.fillForm(driver, "99999990A", "Josefo", "Perez", "77777", "77777");
//		PO_View.getP();
//		// Comprobamos el error de DNI repetido.
//		PO_RegisterView.checkKey(driver, "Error.signup.dni.duplicate", PO_Properties.getSPANISH());
//		// Rellenamos el formulario.
//		PO_RegisterView.fillForm(driver, "99999990B", "Jose", "Perez", "77777", "77777");
//		// Comprobamos el error de Nombre corto.
//		PO_RegisterView.checkKey(driver, "Error.signup.name.length", PO_Properties.getSPANISH());
//		// Rellenamos el formulario.
//		PO_RegisterView.fillForm(driver, "99999990B", "Josefo", "Per", "77777", "77777");
//		// Comprobamos el error de Apellidos cortos
//		PO_RegisterView.checkKey(driver, "Error.signup.lastName.length", PO_Properties.getSPANISH());
//		// Rellenamos el formulario.
//		PO_RegisterView.fillForm(driver, "99999990B", "Josefo", "Perez", "12", "12");
//		// Comprobamos el error de Contraseña corta
//		PO_RegisterView.checkKey(driver, "Error.signup.password.length", PO_Properties.getSPANISH());
//		// Rellenamos el formulario.
//		PO_RegisterView.fillForm(driver, "99999990B", "Josefo", "Perez", "123456", "123457");
//		// Comprobamos el error de Contraseñas no coincidiendo
//		PO_RegisterView.checkKey(driver, "Error.signup.passwordConfirm.coincidence", PO_Properties.getSPANISH());
//	}
//
//	// PR07. Loguearse con éxito desde el Rol de Usuario, 99999990A, 123456
//	@Test
//	public void PR07() {
//		// Vamos al formulario de logueo.
//		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//		// Rellenamos el formulario
//		PO_LoginView.fillForm(driver, "99999990A", "123456");
//		// Comprobamos que entramos en la pagina privada de Alumno
//		PO_View.checkElement(driver, "text", "Notas del usuario");
//	}
//
//	// PR08. Loguearse con éxito desde el Rol de Profesor, 99999993D, 123456
//	@Test
//	public void PR08() {
//		// Vamos al formulario de logueo.
//		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//		// Rellenamos el formulario
//		PO_LoginView.fillForm(driver, "99999993D", "123456");
//		// Comprobamos que entramos en la pagina privada de Profesor
//		PO_View.checkElement(driver, "text", "99999993D");
//	}
//
//	// PR09. Loguearse con éxito desde el Rol de Administrador, 99999988F, 123456
//	@Test
//	public void PR09() {
//		// Vamos al formulario de logueo.
//		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//		// Rellenamos el formulario
//		PO_LoginView.fillForm(driver, "99999988F", "123456");
//		// Comprobamos que entramos en la pagina privada de Administrador
//		PO_View.checkElement(driver, "text", "99999988F");
//	}
//
//	// PR10. Loguearse sin éxito desde el Rol de Alumno, 99999990A, 123456
//	@Test
//	public void PR10() {
//		// Vamos al formulario de logueo.
//		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//		// Rellenamos el formulario
//		PO_LoginView.fillForm(driver, "99999990A", "123458");
//		// Comprobamos que seguimos en el formulario de login
//		PO_View.checkElement(driver, "text", "Identifícate");
//	}
//
//	// PR11. Loguearse con éxito y desconexión desde el Rol de Usuario, 99999990A,
//	// 123456
//	@Test
//	public void PR11() {
//		// Vamos al formulario de logueo.
//		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//		// Rellenamos el formulario
//		PO_LoginView.fillForm(driver, "99999990A", "123456");
//		// Comprobamos que entramos en la pagina privada de Alumno
//		PO_View.checkElement(driver, "text", "Notas del usuario");
//		// Nos desconectamos
//		PO_HomeView.clickOption(driver, "logout", "text", "Identifícate");
//		// Comprobamos que nos devuelve al formulario de login
//		PO_View.checkElement(driver, "text", "Identifícate");
//	}
//
//	// PR12. Loguearse, comprobar que se visualizan 4 filas de notas y desconectarse
//	// usando el rol de estudiante
//	@Test
//	public void PR12() {
//		PO_PrivateView.fillFormCheckPrivateZone(driver, "99999990A", "123456", "Notas del usuario");
//		// Contamos el número de filas de notas
//		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
//				PO_View.getTimeout());
//		assertTrue(elementos.size() == 4);
//		// Ahora nos desconectamos
//		PO_PrivateView.logout(driver, "Identifícate");
//	}
//
//	// PR13. Loguearse como estudiante y ver los detalles de la nota con Descripción
//	// = Nota A2.
//	// P13. Ver la lista de Notas.
//	@Test
//	public void PR13() {
//		PO_PrivateView.fillFormCheckPrivateZone(driver, "99999990A", "123456", "Notas del usuario");
//		SeleniumUtils.esperarSegundos(driver, 1);
//		// Contamos las notas
//		By enlace = By.xpath("//td[contains(text(), 'Nota A2')]/following-sibling::*[2]");
//		driver.findElement(enlace).click();
//		SeleniumUtils.esperarSegundos(driver, 1);
//		// Esperamos por la ventana de detalle
//		PO_View.checkElement(driver, "text", "Detalles de la nota");
//		SeleniumUtils.esperarSegundos(driver, 1);
//		// Ahora nos desconectamos
//		PO_PrivateView.logout(driver, "Identifícate");
//	}
//
//	// PR14. Loguearse como profesor y Agregar Nota A2.
//	// P14. Esta prueba podría encapsularse mejor.
//	@Test
//	public void PR14() {
//		PO_PrivateView.fillFormCheckPrivateZone(driver, "99999993D", "123456", "99999993D");
//		// Pinchamos en la opción de menu de Notas: //li[contains(@id, 'marks-menu')]/a
//		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'marks-menu')]/a");
//		elementos.get(0).click();
//		// Esperamos a que aparezca la opción de añadir nota: //a[contains(@href,
//		// 'mark/add')]
//		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'mark/add')]");
//		// Pinchamos en agregar Nota.
//		elementos.get(0).click();
//		// Ahora vamos a rellenar lanota. //option[contains(@value, '4')]
//		PO_PrivateView.fillFormAddMark(driver, 3, "Nota Nueva 1", "8");
//		// Esperamos a que se muestren los enlaces de paginación en la lista de notas
//		elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
//		// Nos vamos a la última página
//		elementos.get(3).click();
//		// Comprobamos que aparece la nota en la pagina
//		elementos = PO_View.checkElement(driver, "text", "Nota Nueva 1");
//		// Ahora nos desconectamos
//		PO_PrivateView.logout(driver, "Identifícate");
//	}
//
//	// PR15. Loguearse como Profesor, vamos a la última página y eliminamos la Nota
//	// Nueva 1.
//	// PR15. Ver la lista de notas.
//	@Test
//	public void PR15() {
//		PO_PrivateView.fillFormCheckPrivateZone(driver, "99999993D", "123456", "99999993D");
//		// Pinchamos en la opción de menu de Notas: //li[contains(@id, 'marks-menu')]/a
//		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'marks-menu')]/a");
//		elementos.get(0).click();
//		// Pinchamos en la opción de lista de notas.
//		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'mark/list')]");
//		elementos.get(0).click();
//		// Esperamos a que se muestren los enlaces de paginacion la lista de notas
//		elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
//		// Nos vamosa la última página
//		elementos.get(3).click();
//		// Esperamos a que aparezca la Nueva nota en la ultima pagina
//		// Y Pinchamos en el enlace de borrado de la Nota "Nota Nueva 1"
//		// //td[contains(text(), 'Nota Nueva
//		// 1')]/following-sibling::*/a[contains(text(), 'mark/delete')]"
//		elementos = PO_View.checkElement(driver, "free",
//				"//td[contains(text(), 'Nota Nueva 1')]/following-sibling::*/a[contains(@href, 'mark/delete')]");
//		elementos.get(0).click();
//		// Volvemos a la última pagina
//		elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
//		elementos.get(3).click();
//		// Y esperamos a que NO aparezca la ultima "Nueva Nota 1"
//		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Nota Nueva 1", PO_View.getTimeout());
//		// Ahora nos desconectamos
//		PO_PrivateView.logout(driver, "Identifícate");
//	}

}
