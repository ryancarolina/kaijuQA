![](https://i.pinimg.com/originals/16/72/c9/1672c9908cc1fc56b168fca54613c142.jpg)
# KaijuQA
Kaiju is being built to handle a lot of the methods and "features" that I have built many times over as I have changed projects or companies. My plan is to build out this framework so that a QA engineer could clone into the repo and essentially have a test automation framework ready to perform work and scale with ease.

For example, Chromedriver is packaged within the Kaiju framework. The driver for both Windows and Mac OSX is included and logic already in place so that Kaiju will detect the OS type that is attempting to use the driver and choose the correct version. Another example, HTMLUnitDriver is already built into Kaiju so browserless testing is ready out of the box. If you use Sauce Labs or Selenium Grid, support for remote testing is also already built into Kaiju, you simply need to point Kaiju towards the IP of your Selenium hub.

Performance and load testing is ready out of the box thanks to Gatling. Kaiju has example Gatling classes that can be modified to target your web app, which means you get performance testing results in minutes with minimal configuration work.

Nearly all of the methods I've written for Kaiju are generic enough that any web service QA should find them useful and easy to extend or modify. For example, connecting to a database to pull test data or reading from a flat file like a CSV. The overall goal of Kaiju is to give the QA engineer a solid foundation to scale from and prevent them from re-inventing the wheel.

After you clone into the repo, be sure to bring it into your IDE as a maven project. Personally I suggest IntelliJ, but other IDE's should work so long as they support JDK 1.8 and Scala. You will need the Scala plugin in IntelliJ for Gatling performance/load testing to work properly.

In the future I hope to extend Kaijus functionality with the ability to perform chaos testing with tools like Gremlin or Chaos Monkey. I also want to find ways for Kaiju to cover testing infrastructure as code, so when QA encounter Terraform files or Packer scripts, they can assist their team with providing fast feedback.

Questions or comments? Contact me on Linkedin or grab my email from my profile!


