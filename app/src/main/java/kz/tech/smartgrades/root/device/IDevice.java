package kz.tech.smartgrades.root.device;

import kz.tech.smartgrades.MainActivity;
import kz.tech.smartgrades.root.models.ModelDevice;

public interface IDevice {
    String getDeviceGenerationNumber(MainActivity main);
    ModelDevice onDeviceRegistration(MainActivity main, String idAccount);
}
