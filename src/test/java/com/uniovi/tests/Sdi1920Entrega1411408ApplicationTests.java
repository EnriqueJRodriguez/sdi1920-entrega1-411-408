package com.uniovi.tests;

import static org.junit.Assert.assertTrue;

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
import com.uniovi.tests.pageobjects.PO_Publications;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.tests.util.SeleniumUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Sdi1920Entrega1411408ApplicationTests {

	private UsersService userService;

	// Hugo

	// GNU/Linux
//	static String PathFirefox = "/usr/bin/firefox";
//	static String GeckDriver024 = "/home/asuka/Universidad/Tercero/SDI/Lab/SecondPart/Other_files/Materials/geckodriver";

	// Enrique

	// Windows
	static String PathFirefox = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
	static String GeckDriver024 = "C:\\Users\\EnriqueJRodriguez\\Downloads\\OneDrive_2020-03-02\\PL-SDI-Ses5-material\\geckodriver024win64.exe";

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
//			Assert.notNull(userService.getUserByEmail("marcos@sdiuniovi.es").getLastName().equals("Rupertez"),
//					"Usuario No Encontrado");
		// Nos desconectamos
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
		// Comprobamos que nos salta un error en los campos del formulario
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
		// Comprobamos que nos salta un error en los campos del formulario
		PO_RegisterView.checkKey(driver, "Error.signup.passwordConfirm.coincidence", PO_Properties.getSPANISH());
//				Assert.isNull(userService.getUserByEmail("albeto@uniovi.es"),"Usuario No Encontrado");
	}

	// PR04. Registro de Usuario con datos inválidos (email existente).
	@Test
	public void PR04() {
		String repeatedEmail = "marcos@sdiuniovi.es"; // Usamos el del test anterior por defecto
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, repeatedEmail, "Alberto", "Monzon", "123456", "12");
		// Comprobamos que nos salta un error en los campos del formulario
		PO_RegisterView.checkKey(driver, "Error.signup.email.duplicate", PO_Properties.getSPANISH());
//				Assert.isTrue(!userService.getUserByEmail(repeatedEmail).getLastName().equals("Monzon"), "El usuario repetido no se inserto" );
	}

	// PR05. Inicio de sesión con datos válidos (administrador).
	@Test
	public void PR05() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "juansdi@uniovi.es", "123456");
		// Comprobamos que entramos en la pagina privada de usuarios
		PO_NavView.checkKey(driver, "nav.message.users", PO_Properties.getSPANISH());
		// Nos desconectamos
		PO_PrivateView.logout(driver);
	}

	// PR06. Inicio de sesión con datos válidos (usuario estándar).
	@Test
	public void PR06() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "jaimitosdi@uniovi.es", "123456");
		// Comprobamos que entramos en la pagina privada de usuarios
		PO_NavView.checkKey(driver, "nav.message.users", PO_Properties.getSPANISH());
		// Nos desconectamos
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
		// Comprobamos que seguimos en la pagina de inicio
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
		// Comprobamos que seguimos en la pagina de inicio
		PO_LoginView.checkKey(driver, "login.title", PO_Properties.getSPANISH());
	}

	// PR09. Hacer click en la opción de salir de sesión y comprobar que se redirige
	// a la página de inicio de sesión (Login).
	@Test
	public void PR09() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "jaimitosdi@uniovi.es", "123456");
		// Comprobamos que entramos en la pagina privada de usuarios
		PO_LoginView.checkKey(driver, "users.title", PO_Properties.getSPANISH());
		// Nos desconectamos
		PO_PrivateView.logout(driver);
		// Comprobamos que estamos en la pantalla de inicio
		PO_LoginView.checkKey(driver, "login.title", PO_Properties.getSPANISH());
		// Comprobamos que no podemos acceder a la pestaña de usuarios
		PO_View.checkNoElement(driver, "users.title", PO_Properties.getSPANISH());
	}

	// PR10. Comprobar que el botón cerrar sesión no está visible si el usuario no
	// está autenticado.
	@Test
	public void PR10() {
		PO_NavView.checkNoElement(driver, "logout.message", PO_Properties.getSPANISH());
	}

	// PR11. Mostrar el listado de usuarios y comprobar que se muestran todos los
	// que existen en el sistema.
	@Test
	public void PR11() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "jaimitosdi@uniovi.es", "123456");
		// Comprobamos que aparecen usurios normales
		PO_View.checkElement(driver, "text", "max@arcadia.com");
		PO_View.checkElement(driver, "text", "pepitosdi@uniovi.es");
		PO_View.checkElement(driver, "text", "laurapalmer@twinpeaks.com");
		// Comprobamos que no sale el administrador
		PO_View.checkNoElement(driver, "juansdi@uniovi.es");
		// Comprobamos que no sale el propio usuario logueado
		PO_View.checkNoElement(driver, "jaimitosdi@uniovi.es");

		// Nos desconectamos
		PO_PrivateView.logout(driver);
	}

	// PR12. Hacer una búsqueda con el campo vacío y comprobar que se muestra la
	// página que
	// corresponde con el listado usuarios existentes en el sistema.
	@Test
	public void PR12() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "jaimitosdi@uniovi.es", "123456");
		// Realizamos la busqueda
		PO_PrivateView.search(driver, "");
		// Comprobamos que aparecen usurios normales
		PO_View.checkElement(driver, "text", "max@arcadia.com");
		PO_View.checkElement(driver, "text", "pepitosdi@uniovi.es");
		PO_View.checkElement(driver, "text", "laurapalmer@twinpeaks.com");
		// Comprobamos que no sale el administrador
		PO_View.checkNoElement(driver, "juansdi@uniovi.es");
		// Comprobamos que no sale el propio usuario logueado
		PO_View.checkNoElement(driver, "jaimitosdi@uniovi.es");
		// Nos desconectamos
		PO_PrivateView.logout(driver);
	}

	// PR13. Hacer una búsqueda escribiendo en el campo un texto que no exista y
	// comprobar que se
	// muestra la página que corresponde, con la lista de usuarios vacía
	@Test
	public void PR13() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "jaimitosdi@uniovi.es", "123456");
		// Realizamos la busqueda
		PO_PrivateView.search(driver, "---------------------------------");
		// Comprobamos que no aparecen usurios
		PO_View.checkNoElement(driver, "max@arcadia.com");
		PO_View.checkNoElement(driver, "pepitosdi@uniovi.es");
		PO_View.checkNoElement(driver, "laurapalmer@twinpeaks.com");
		PO_View.checkNoElement(driver, "juansdi@uniovi.es");
		PO_View.checkNoElement(driver, "jaimitosdi@uniovi.es");
		// Nos desconectamos
		PO_PrivateView.logout(driver);
	}

	// PR14. Hacer una búsqueda con un texto específico y comprobar que se muestra
	// la página que
	// corresponde, con la lista de usuarios en los que el texto especificados sea
	// parte de su nombre, apellidos o de su email.
	@Test
	public void PR14() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "jaimitosdi@uniovi.es", "123456");
		// Realizamos la busqueda
		PO_PrivateView.search(driver, "laurapalmer@twinpeaks.com");
		// Comprobamos que solo se carga una pagina
		PO_View.checkKey(driver, "pagination.first", PO_Properties.getSPANISH());
		PO_View.checkElement(driver, "text", "1");
		PO_View.checkKey(driver, "pagination.last", PO_Properties.getSPANISH());
		// comprobamos que solo aparece el usuario buscado
		PO_View.checkElement(driver, "text", "laurapalmer@twinpeaks.com");
		// Comprobamos que no aparecen el resto usurios
		PO_View.checkNoElement(driver, "max@arcadia.com");
		PO_View.checkNoElement(driver, "pepitosdi@uniovi.es");
		PO_View.checkNoElement(driver, "juansdi@uniovi.es");
		PO_View.checkNoElement(driver, "jaimitosdi@uniovi.es");
		// Nos desconectamos
		PO_PrivateView.logout(driver);
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
				"/html/body/div[1]/div[1]/table/tbody/tr[td[contains(text(), 'jaimitosdi@uniovi.es')]]/td[4]/div/button");
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
		// Nos desconectamos
		PO_PrivateView.logout(driver);
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
				"/html/body/div[1]/div[1]/table/tbody/tr[td[contains(text(), 'max@arcadia.com')]]/td[4]");
		// Comprobamos que el botón no se muestra ya que existe una invitación
		// entre éstos dos usuarios
		assertEquals(true, botones.get(0).getText().equals(""));
		// Nos desconectamos
		PO_PrivateView.logout(driver);
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
		elementos = PO_View.checkElement(driver, "free", "//td/div");
		assertEquals(2, elementos.size());
		elementos.get(0).findElement(By.className("btn")).click();
		driver.navigate().refresh();
		elementos = PO_View.checkElement(driver, "free", "//td/div");
		assertEquals(1, elementos.size());
		// Nos desconectamos
		PO_PrivateView.logout(driver);
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
		elementos = PO_View.checkElement(driver, "free", "//td[contains(text(), 'jaimitosdi@uniovi.es')]");
		assertEquals(1, elementos.size());
		// Nos desconectamos
		PO_PrivateView.logout(driver);
	}

	// PR20. Visualizar al menos cuatro páginas en Español/Inglés/Español
	@Test
	public void PR20() {
		// Hacemos las comprobaciones en español
		pr20checks(PO_Properties.getSPANISH());
		// Cambiamos de idioma a ingles
		PO_NavView.changeIdiom(driver, PO_View.getP().getString("language.en", PO_Properties.getSPANISH()));
		// Hacemos las comprobaciones en ingles
		pr20checks(PO_Properties.getENGLISH());
		// Cambiamos de idioma a español de nuevo
		PO_NavView.changeIdiom(driver, PO_View.getP().getString("language.es", PO_Properties.getENGLISH()));
		// Hacemos las comprobaciones en español
		pr20checks(PO_Properties.getSPANISH());
	}

	// PR21. Intentar acceder sin estar autenticado a la opción de listado de usuarios. Se deberá volver al
	//       formulario de login
	@Test
	public void PR21() {
		// Intentamos acceder al listado de usuarios
		driver.get(URL+"/user/list");
		// Comprobamos que estamos en el login
		PO_View.checkKey(driver, "login.title", PO_Properties.getSPANISH());
	}
	
	// PR22. Intentar acceder sin estar autenticado a la opción de listado de publicaciones de un usuario
	//       estándar. Se deberá volver al formulario de login.
	@Test
	public void PR22() {
		// Intentamos acceder al listado de usuarios
		driver.get(URL+"/publication/list");
		// Comprobamos que estamos en el login
		PO_View.checkKey(driver, "login.title", PO_Properties.getSPANISH());
	}	

	// PR23. Estando autenticado como usuario estándar intentar acceder a una opción disponible solo
	//       para usuarios administradores (Se puede añadir una opción cualquiera en el menú). Se deberá indicar un
	//       mensaje de acción prohibida.
	@Test
	public void PR23() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario con un usuario estandar
		PO_LoginView.fillForm(driver, "max@arcadia.com", "123456");
		// Comprobamos que entramos en la pagina privada del usuario
		PO_NavView.checkElement(driver, "text",
					PO_NavView.getP().getString("nav.message.users", PO_Properties.getSPANISH()));		
		// Intentamos acceder al listado de usuarios.
		driver.get(URL+"/user/all");
		// Comprobamos que nos salta el error de accion prohibida (HTTP STATUS 403).
		assertEquals(true, driver.getTitle().equals("HTTP Status 403 – Forbidden"));
		// Nos desconectamos
		driver.navigate().back();
		PO_PrivateView.logout(driver);
	}
	
	// PR24. Ir al formulario crear publicaciones, rellenarla con datos válidos y pulsar el botón Submit.
	//		 Comprobar que la publicación sale en el listado de publicaciones de dicho usuario.
	@Test
	public void PR24() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario con un usuario estandar
		PO_LoginView.fillForm(driver, "max@arcadia.com", "123456");
		// Comprobamos que entramos en la pagina privada del usuario
		PO_NavView.checkElement(driver, "text",
					PO_NavView.getP().getString("nav.message.users", PO_Properties.getSPANISH()));		
		// Accedemos al formulario de crear publicaciones.
		driver.get(URL+"/publication/add");
		// Comprobamos que entramos en la pagina publicaciones
		PO_NavView.checkKey(driver,"publications.add.title", PO_Properties.getSPANISH());
		// Rellenamos el formulario
		PO_Publications.fillForm(driver, "Primera Publicacion", "Esta es mi primera publicacion");
		// Accedemos al listado de mis publicaciones
		driver.get(URL+"/publication/list");
		// Comprobamos que entramos en la pagina publicaciones
		PO_NavView.checkKey(driver,"publications.title", PO_Properties.getSPANISH());
		// Comprobamos que esta la publicacion
		PO_View.checkElement(driver, "free",
				"/html/body/div[1]/div[1]/table/tbody/tr[td[contains(text(), 'Primera Publicacion')]]");
		// Nos desconectamos
		PO_PrivateView.logout(driver);
	}
	
	// PR25. Ir al formulario de crear publicaciones, rellenarla con datos inválidos (campo título vacío) y
	//		 pulsar el botón Submit. Comprobar que se muestra el mensaje de campo obligatorio.
	@Test
	public void PR25() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario con un usuario estandar
		PO_LoginView.fillForm(driver, "max@arcadia.com", "123456");
		// Comprobamos que entramos en la pagina privada del usuario
		PO_NavView.checkElement(driver, "text",
					PO_NavView.getP().getString("nav.message.users", PO_Properties.getSPANISH()));		
		// Accedemos al formulario de crear publicaciones.
		driver.get(URL+"/publication/add");
		// Comprobamos que entramos en la pagina publicaciones
		PO_Publications.checkKey(driver,"publications.add.title", PO_Properties.getSPANISH());
		// Rellenamos el formulario
		PO_Publications.fillForm(driver, "", "Esta es mi primera publicacion");
		// Comprobamos el mensaje de error
		PO_Publications.checkKey(driver, "Error.empty", PO_Properties.getSPANISH());
		// Comprobamos que seguimos en la pestaña de añadir publicaciones
		PO_Publications.checkKey(driver,"publications.add.title", PO_Properties.getSPANISH());
		// Accedemos al listado de mis publicaciones
		driver.get(URL+"/publication/list");
		// Comprobamos que entramos en la pagina publicaciones
		PO_Publications.checkKey(driver,"publications.title", PO_Properties.getSPANISH());
		// Comprobamos que esta la publicacion del test anterior + publicacion por defecto
		List<WebElement> elementos = PO_Publications.checkElement(driver, "free",
				"/html/body/div[1]/div[1]/table/tbody/tr");
		assertEquals(2, elementos.size());
		// Nos desconectamos
		PO_PrivateView.logout(driver);
	}

	// PR26. Mostrar el listado de publicaciones de un usuario y comprobar que se muestran todas las que
	//       existen para dicho usuario. 
	@Test
	public void PR26() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario con un usuario estandar
		PO_LoginView.fillForm(driver, "max@arcadia.com", "123456");
		// Comprobamos que entramos en la pagina privada del usuario
		PO_NavView.checkElement(driver, "text",
					PO_NavView.getP().getString("nav.message.users", PO_Properties.getSPANISH()));		
		// Accedemos al listado de mis publicaciones
		driver.get(URL+"/publication/list");
		// Comprobamos que entramos en la pagina publicaciones
		PO_Publications.checkKey(driver,"publications.title", PO_Properties.getSPANISH());
		// Comprobamos que esta la publicacion del test anterior + publicacion por defecto
		List<WebElement> elementos = PO_Publications.checkElement(driver, "free",
				"/html/body/div[1]/div[1]/table/tbody/tr");
		assertEquals(2, elementos.size());
		// Nos desconectamos
		PO_PrivateView.logout(driver);
	}

	// PR31. Mostrar el listado de usuarios y comprobar que se muestran todos los que existen en el sistema. 
	@Test
	public void PR31() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario con usuario admin
		PO_LoginView.fillForm(driver, "legoshi@beastars.es", "123456");
		// Navegamos hasta la opcion de menu de administrador
		driver.get(URL+"/user/all");
		// Comprobamos que aparecen usurios normales
		PO_View.checkElement(driver, "text", "max@arcadia.com");
		PO_View.checkElement(driver, "text", "pepitosdi@uniovi.es");
		PO_View.checkElement(driver, "text", "laurapalmer@twinpeaks.com");
		PO_View.checkElement(driver, "text", "jaimitosdi@uniovi.es");
		// Comprobamos que sale el otro administrador
		PO_View.checkElement(driver, "text", "juansdi@uniovi.es");
		// Comprobamos que no sale el propio usuario logueado
		PO_View.checkNoElement(driver, "legoshi@beastars.es");
		// Nos desconectamos
		PO_PrivateView.logout(driver);
	}
	
	private void pr20checks(int language) {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");

		// Comprobamos que estamos en el login
		PO_View.checkKey(driver, "login.title", language);
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "max@arcadia.com", "123456");

		// Comprobamos que entramos en la pagina privada del usuario
		PO_NavView.checkElement(driver, "text", PO_NavView.getP().getString("nav.message.users", language));

		List<WebElement> elementos;

		// Pinchamos en la opción de menú de amigos
		elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'friends-menu')]/a");
		elementos.get(0).click();
		// Pinchamos en la opción de lista de amigos.
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, '/friend/list')]");
		elementos.get(0).click();
		// Miramos que estamos en la vista de amigos
		PO_View.checkKey(driver, "friends.title", language);

		// Pinchamos en la opción de menu de invitaciones
		elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'invitations-menu')]/a");
		elementos.get(0).click();
		// Pinchamos en la opción de lista de amigos.
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, '/invitation/list')]");
		elementos.get(0).click();
		// Miramos que estamos en la vista de amigos
		PO_View.checkKey(driver, "invitations.title", language);

		// Nos desconectamos
		PO_PrivateView.logout(driver);
	}

}
