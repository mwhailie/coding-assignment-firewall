package com.illumio;

import org.junit.Assert;
import org.junit.Test;

public class Driver {

    private Firewall firewall;

    public Driver() {
        firewall = new Firewall("fw.csv");
    }

    @Test
    public void successfulTest() {
        Assert.assertTrue("return true when match first rule", firewall.accept_packet("inbound", "tcp", 80, "192.168.1.2"));
        Assert.assertTrue("return true when match second rule", firewall.accept_packet("inbound", "udp", 53, "192.168.2.1"));
    }

    @Test
    public void invalidDirectionTest(){
        Assert.assertTrue("return false when invalid direction", !firewall.accept_packet("invalid", "tcp", 80, "192.168.1.2"));
    }

    @Test
    public void invalidProtocolTest(){
        Assert.assertTrue("return false when invalid protocol", !firewall.accept_packet("inbound", "invalid", 80, "192.168.1.2"));
    }

    @Test
    public void invalidPortTest(){
        Assert.assertTrue("return false when invalid port", !firewall.accept_packet("inbound", "tcp", 65536, "192.168.1.2"));
    }

    @Test
    public void invalidIpTest(){
        Assert.assertTrue("return false when invalid ip", !firewall.accept_packet("inbound", "tcp", 80, "255.255.255.256"));
    }
    @Test
    public void invalidFormatIpTest(){
        Assert.assertTrue("return false when invalid ip", !firewall.accept_packet("inbound", "tcp", 80, "invalid"));
    }
    @Test
    public void portNotExistTest(){
        Assert.assertTrue("return false when no matching port", !firewall.accept_packet("inbound", "tcp", 79, "192.168.1.2"));
    }

    @Test
    public void ipNotExistTest(){
        Assert.assertTrue("return false when no matching ip", !firewall.accept_packet("inbound", "tcp", 80, "192.168.2.5"));
    }

}
