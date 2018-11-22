package com.illumio;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * The Firewall program implements a firewall that takes a set of predetermined security rules.
 * As network traffic enters and leaves the machine, the firewall rules determine whether the traffic should be allowed or blocked.
 *
 * @author  Wenhe Ma
 *
 */
public class Firewall {

    private Map<String, Map<Integer, Set<Interval>>> rules;

    /**
     * Creates a Firewall with the given CSV file.
     * @param filePath a file path to a CSV file
     */
    public Firewall(String filePath)  {
        rules = new HashMap<>();
        rules.put("inbound_tcp", new HashMap<>());
        rules.put("outbound_tcp", new HashMap<>());
        rules.put("inbound_udp", new HashMap<>());
        rules.put("outbound_udp", new HashMap<>());
        File file = new File(filePath);
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while(sc.hasNext()){
            addRule(sc.next());
        }
        sc.close();
    }

    private void addRule(String input) {
        String[] inputs = input.split(",");
        String direction = inputs[0];
        String protocol = inputs[1];
        String key = direction.toLowerCase() + "_" + protocol.toLowerCase();
        if(!rules.containsKey(key)){
            return;
        }
        String port = inputs[2];
        String ipRange = inputs[3];

        Interval ipInteval;
        try {
            if (ipRange.indexOf("-") == -1) {
                ipInteval = new Interval(ipAddressToInteger(ipRange), ipAddressToInteger(ipRange));
            } else {
                ipInteval = new Interval(ipAddressToInteger(ipRange.split("-")[0]), ipAddressToInteger(ipRange.split("-")[1]));
            }
            if(port.indexOf("-") == -1){
                rules.get(key).compute(Integer.parseInt(port),(k, v) -> v != null ? v : new HashSet<>()).add(ipInteval);
            }else{
                for(int i = Integer.parseInt(port.split("-")[0]); i < Integer.parseInt(port.split("-")[1]); i ++){
                    rules.get(key).compute( i, (k, v) -> v != null ? v : new HashSet<>()).add(ipInteval);
                }
            }
        }catch (NumberFormatException e){
            return;
        }

    }

    /**
     * Determine whether the traffic with given parameters should be allowed or blocked.
     * @param direction “inbound” or “outbound”
     * @param protocol exactly one of “tcp” or “udp”, all lowercase
     * @param port an integer in the range [1, 65535]
     * @param ip_address a single well-formed IPv4 address
     * @return true if there exists a rule in the firewall that allows traffic with these particular parameter,
     *          and false otherwise.
     */
    public boolean accept_packet(String direction, String protocol, int port, String ip_address) {
        String key = direction.toLowerCase() + "_" + protocol.toLowerCase();
        if(!rules.containsKey(key)){
            return false;
        }
        long ip;

        try{
            ip = ipAddressToInteger(ip_address);
        }catch (NumberFormatException e){
            return false;
        }

        if(port < 0 || port > 65535 || ip < 0 || ip > Math.pow(2,32) - 1){
            return false;
        }
        if(!rules.get(key).containsKey(port)){
            return false;
        }

        Set<Interval> ipRanges = rules.get(key).get(port);

        for(Interval ipRange : ipRanges){
            if(ipRange.contains(ip)){
                return true;
            }
        }
        return false;
    }

    private long ipAddressToInteger(String ip) {
        long ans = 0;
        for (String x: ip.split("\\.")) {
            ans = 256 * ans + Integer.valueOf(x);
        }
        return ans;
    }

}
