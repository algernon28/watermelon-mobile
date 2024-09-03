package com.hype.mobile.pages.home;

import com.google.inject.Inject;
import com.watermelon.core.MobilePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DettagliPage extends MobilePage {

    @AndroidFindBy(xpath = "//*[@resource-id='it.hype.app:id/row']/descendant::*[@text='Saldo contabile']")
    protected WebElement lblSaldoContabile;
    @AndroidFindBy(xpath = "(//*[@text='Saldo contabile']/ancestor::*[@resource-id='it.hype.app:id/row'])/descendant::*[@resource-id='it.hype.app:id/container_right']")
    protected WebElement totSaldoContabile;
    @AndroidFindBy(xpath = "//*[@resource-id='it.hype.app:id/row']/descendant::*[@text='Disponibilità']")
    protected WebElement lblDisponibilita;
    @AndroidFindBy(xpath = "(//*[@text='Disponibilità']/ancestor::*[@resource-id='it.hype.app:id/row'])/descendant::*[@resource-id='it.hype.app:id/container_right']")
    protected WebElement totDisponibilita;
    @AndroidFindBy(xpath = "//*[@resource-id='it.hype.app:id/row']/descendant::*[@text='Pagamenti non contabilizzati']")
    protected WebElement lblPagamentiNonContabilizzati;
    @AndroidFindBy(xpath = "(//*[@text='Pagamenti non contabilizzati']/ancestor::*[@resource-id='it.hype.app:id/row'])/descendant::*[@resource-id='it.hype.app:id/container_right']")
    protected WebElement totPagamentiNonContabilizzati;
    @AndroidFindBy(xpath = "//*[@resource-id='it.hype.app:id/row']/descendant::*[@text='Impegnato per risparmi']")
    protected WebElement lblImpegnatoRisparmi;
    @AndroidFindBy(xpath = "(//*[@text='Impegnato per risparmi']/ancestor::*[@resource-id='it.hype.app:id/row'])/descendant::*[@resource-id='it.hype.app:id/container_right']")
    protected WebElement totImpegnatoRisparmi;
    @AndroidFindBy(id = "it.hype.app:id/recycler")
    private WebElement container;

    @Inject
    public DettagliPage(AppiumDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @Override
    public boolean isLoaded() {
        return checkIfLoaded(container);
    }


}
