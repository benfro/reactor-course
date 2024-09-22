package net.benfro.lab.reactor.assignment_6.revenue;

import lombok.extern.slf4j.Slf4j;
import net.benfro.lab.reactor.assignment_6.DataStore;

//revenue per category
@Slf4j
public class RevenueData extends DataStore<String, Integer> {

    public RevenueData() {
        super();
    }

    public String report() {
        log.info("Starting to generate Revenue Report");
        var sb = new StringBuilder();
        sb.append("Revenue report:\n");
        super.report(sb);
        return sb.toString();
    }

    public void logState() {
        super.logState();
    }
}
