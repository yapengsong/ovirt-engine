package org.ovirt.engine.ui.uicompat;

public interface CommonApplicationConstantsWithLookup extends com.google.gwt.i18n.client.ConstantsWithLookup {
    @DefaultStringValue("stateless")
    String stateless();

    @DefaultStringValue("vdsGroupId")
    String vdsGroupId();

    @DefaultStringValue("memSizeMb")
    String memSizeMb();

    @DefaultStringValue("numOfIoThreads")
    String numOfIoThreads();

    @DefaultStringValue("numOfSockets")
    String numOfSockets();

    @DefaultStringValue("cpuPerSocket")
    String cpuPerSocket();

    @DefaultStringValue("threadsPerCpu")
    String threadsPerCpu();

    @DefaultStringValue("customEmulatedMachine")
    String customEmulatedMachine();

    @DefaultStringValue("customCpuName")
    String customCpuName();

    @DefaultStringValue("numOfMonitors")
    String numOfMonitors();

    @DefaultStringValue("singleQxlPci")
    String singleQxlPci();

    @DefaultStringValue("timeZone")
    String timeZone();

    @DefaultStringValue("usbPolicy")
    String usbPolicy();

    @DefaultStringValue("defaultBootSequence")
    String defaultBootSequence();

    @DefaultStringValue("niceLevel")
    String niceLevel();

    @DefaultStringValue("cpuShares")
    String cpuShares();

    @DefaultStringValue("ssoMethod")
    String ssoMethod();

    @DefaultStringValue("kernelUrl")
    String kernelUrl();

    @DefaultStringValue("kernelParams")
    String kernelParams();

    @DefaultStringValue("initrdUrl")
    String initrdUrl();

    @DefaultStringValue("userDefinedProperties")
    String userDefinedProperties();

    @DefaultStringValue("predefinedProperties")
    String predefinedProperties();

    @DefaultStringValue("customProperties")
    String customProperties();

    @DefaultStringValue("defaultDisplayType")
    String defaultDisplayType();

    @DefaultStringValue("vncKeyboardLayout")
    String vncKeyboardLayout();

   @DefaultStringValue("minAllocatedMem")
   String minAllocatedMem();

   @DefaultStringValue("runAndPause")
   String runAndPause();

   @DefaultStringValue("SerialNumberPolicy")
   String SerialNumberPolicy();

   @DefaultStringValue("customSerialNumber")
   String customSerialNumber();

   @DefaultStringValue("bootMenuEnabled")
   String bootMenuEnabled();

   @DefaultStringValue("spiceFileTransferEnabled")
   String spiceFileTransferEnabled();

   @DefaultStringValue("spiceCopyPasteEnabled")
   String spiceCopyPasteEnabled();



}
