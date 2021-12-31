package kz.tech.smartgrades.root.hardware_access;

public interface IHardwareAccess {
    boolean isToReadNetworkHistory();
    boolean isPhoneAccess();
    void onPhoneAccess();
}
