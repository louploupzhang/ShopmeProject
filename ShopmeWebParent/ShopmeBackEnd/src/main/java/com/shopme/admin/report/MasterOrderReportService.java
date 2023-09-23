package com.shopme.admin.report;

import com.lowagie.text.pdf.AcroFields;
import com.shopme.admin.order.OrderRepository;
import com.shopme.common.entity.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class MasterOrderReportService {
    private DateFormat dateFormatter;
    @Autowired
    private OrderRepository repo;

    public List<ReportItem> getReportDataLast7Days() {
        return getReportDataLastXDays(7);
    }

    public List<ReportItem> getReportDataLast28Days() {
        return getReportDataLastXDays(28);
    }

    private List<ReportItem> getReportDataLastXDays(int days) {
        Date endTime = new Date();
        Calendar cal = Calendar.getInstance();
        //Go back to the day
        cal.add(Calendar.DAY_OF_MONTH, -(days - 1));
        Date startTime = cal.getTime();

        System.out.println("Start time: " + startTime);
        System.out.println("End time: " + endTime);

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        return getReportDataByDateRange(startTime, endTime);
    }

    private List<ReportItem> getReportDataByDateRange(Date startTime, Date endTime) {
        List<Order> listOrders = repo.findByOrderTimeBetween(startTime, endTime);
        printRawData(listOrders);

        List<ReportItem> listReportItems = createReportData(startTime, endTime);

        calculateSalesForReportData(listOrders, listReportItems);
        printReportData(listReportItems);

        return listReportItems;
    }

    private void calculateSalesForReportData(List<Order> listOrders, List<ReportItem> listReportItems) {
        for (Order order : listOrders) {
            String orderDateString = dateFormatter.format(order.getOrderTime());

            ReportItem reportItem = new ReportItem(orderDateString);

            int itemIndex = listReportItems.indexOf(reportItem);

            if (itemIndex >= 0) {
                reportItem = listReportItems.get(itemIndex);
                reportItem.addGrossSales(order.getTotal());
                reportItem.addNetSales(order.getSubtotal() - order.getProductCost());
                reportItem.increaseOrdersCount();
            }
        }
    }

    private void printReportData(List<ReportItem> listReportItems) {
        listReportItems.forEach(item -> {
            System.out.printf("%s, %10.2f, %10.2f, %d \n", item.getIdentifier(), item.getGrossSales(),
                    item.getNetSales(), item.getOrdersCount());
        });
    }

    private List<ReportItem> createReportData(Date startTime, Date endTime) {
        List<ReportItem> listReportItems = new ArrayList<>();

        Calendar startDate = Calendar.getInstance();
        startDate.setTime(startTime);
        Calendar endDate = Calendar.getInstance();
        endDate.setTime(endTime);

        Date currentDate = startDate.getTime();
        String dateString = dateFormatter.format(currentDate);

        listReportItems.add(new ReportItem(dateString)); //Identifier(date)

        do {
            startDate.add(Calendar.DAY_OF_MONTH, 1);
            currentDate = startDate.getTime();
            dateString = dateFormatter.format(currentDate);

            listReportItems.add(new ReportItem(dateString));
        } while (startDate.before(endDate));

        return listReportItems;
    }

    private void printRawData(List<Order> listOrders) {
        listOrders.forEach(order -> System.out.printf("%-3d | %s | %10.2f | %10.2f \n",
                order.getId(), order.getOrderTime(), order.getTotal(), order.getProductCost()));
    }
}
