import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Main {
	
	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		FileWriter myWriter = new FileWriter("temp.txt",false);
		FileWriter myWriter2 = new FileWriter("temp4.txt",false);

		String url ="https://www.google.com/search?q=";
		String[] searchtext= {"kgs 254 m","bosch POF 1400","metabo kgs 216","Samsung Tab s7",
				"Samsung Tab s6 Lite","Samsung Galaxy Note 10","Galaxy Note 10 plus","TOMTOM Start 52","Garmin Drive 51 LMT-S"};

		String myInput=searchtext[2];//keyword of searching product

		System.setProperty("webdriver.chrome.driver", "C:\\selenium-java-3.141.59\\chromedriver_win32\\chromedriver.exe");
		
		WebDriver driver = new ChromeDriver();
		
		driver.get(url+myInput);
		
		Thread.sleep(500);//to avoid detected as a robot/automation
		
		//position of button "image" and "shopping" is dynamic, then the procedure below is needed to find 
		//exact position of button "shopping", when it is found then action "click"
		List<WebElement> allXpath = driver.findElements(By.tagName("a"));//find all elements  with tagName "a" (other "div","span" etc)
		for ( WebElement e : allXpath ) {
			System.out.println(e.getText());
			myWriter2.write("get a: "+e.getText());
			myWriter2.write("\n");
			if(e.getText().equals("Shopping")) {
				System.out.println("get Shopping");
				myWriter2.write("get a: "+e.getText());
				myWriter2.write("\n");
				e.click();
				break;
			}
		}
		
		Thread.sleep(500);//to avoid detected as robot/automation (there/above is a action "click") 
		
		driver.manage().window().maximize();

		WebDriverWait wait = new WebDriverWait(driver, 40);
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("top-pla-group-inner")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("rso")));//"rso": id for group of this product (keyword/search text)

		List<WebElement> allElements = driver.findElements(By.className("sh-dlr__list-result"));//find all elements under className "sh-dlr__list-result"

		int i=0;
		for ( WebElement e : allElements ) {
			i++;
			System.out.println(e.getTagName());
//			myWriter.write("#"+i+" "+e.getTagName()+"Atr: "+e.getClass().getName().toString());
			System.out.println("get 2");
			myWriter2.write("get #"+i);
			myWriter2.write("\n");
			myWriter2.write(e.getText().toString());
			myWriter2.write("\n");
			myWriter2.write("=================================================================");
			myWriter2.write("\n\n");
			
			if(textVerify(e.getText().toString(),myInput)==true) {
				myWriter.write("get #"+i);
				myWriter.write("\n");
				myWriter.write(e.getText().toString());
				myWriter.write("\n");
				myWriter.write("=================================================================");
				myWriter.write("\n\n");
			};
			
		}
		
		Thread.sleep(500);//to avoid detected as robot/automation
		driver.quit();//quit from all ; driver.close() -- close current tab
		myWriter.close();
		myWriter2.close();
		System.out.println("Program End..");
	}
	
	public static boolean textVerify(String s,String keyword) {
		String[] productInfo = s.split(" ");
		String[] matchInfo = keyword.split(" ");

		int nMatch=0;
		for (int i=0;i<productInfo.length;i++) {
			for (int j=0;j<matchInfo.length;j++) {
				if(matchInfo[j].equals(productInfo[i])) {
					nMatch++;
				}
			}
		}
		
		if(nMatch>=2) {
			return true;
		} 
		else {
			return false;
		}
		
	}
}
