![alt text](https://www.kisspng.com/png-godzilla-reboot-silhouette-kaiju-clip-art-godzilla-742174/)
# kaijuQA
Automated test framework

Kaiju is being built to handle a lot of the methods and "features" that I have built many times over as I have changed projects or companies. My plan is to build out this framework so that a QA engineer could clone into the repo and essentially have a test automation framework ready to perform work and scale with ease.

For example, Chromedriver is packaged within the Kaiju framework. The driver for both Windows and Mac OSX is included and logic already in place so that Kaiju will detect the OS type that is attempting to use the driver and choose the correct version. Another example, HTMLUnitDriver is already built into Kaiju so browserless testing is ready out of the box. If you use Sauce Labs or Selenium Grid, support for remote testing is also already built into Kaiju.

After you clone into the repo, besure to bring this into your IDE as a maven project. Personally I suggest intellij, but other IDE's should work so long as they support JDK 1.8 and scala. You will need the scala plugin in intellij for gatling performance/load testing to work properly.

04 APR 2019

Kaiju currently uses RestASSURED for API testing, Gatling for peformance and load testing, and selenium/webdriver for UI functional testing.
