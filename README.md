# kaijuQA
Automated test framework

Kaiju is being built to handle a lot of the methods and "features" that I have built many times over as I have changed projects or companies. My plan is to build out this framework so that a QA engineer could clone into the repo and essentially have a test automation framework ready to perform work and scale with ease.

For example, Chromedriver is packaged within the Kaiju framework. The driver for both Windows and Mac OSX is included and logic already in place so that Kaiju will detect the OS type that is attempting to use the driver and choose the correct version. Another example, HTMLUnitDriver is already built into Kaiju so browserless testing is ready out of the box. If you use Sauce Labs or Selenium Grid, support for remote testing is also already built into Kaiju.

In the future, Kaiju will support built in functionality for API, performance, load, security, and many other types of testing. All in an effort to build a framework that ANY QA professional would find useful and time efficient.


04 APR 2019

Kaiju currently uses RestASSURED for API testing, Gatling for peformance and load testing, and selenium/webdriver for UI functional testing.
