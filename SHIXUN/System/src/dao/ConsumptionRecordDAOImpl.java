package dao;

import entity.ConsumptionRecord;
import java.util.ArrayList;
import java.util.List;

public class ConsumptionRecordDAOImpl {
    private static final List<ConsumptionRecord> list = new ArrayList<>();

    public void add(ConsumptionRecord record) {
        list.add(record);
    }

    public List<ConsumptionRecord> getAll() {
        return new ArrayList<>(list);
    }
}