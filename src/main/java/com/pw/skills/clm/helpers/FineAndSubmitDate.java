package com.pw.skills.clm.helpers;

import com.pw.skills.clm.entities.BookIssued;
import com.pw.skills.clm.repositories.BookIssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class FineAndSubmitDate {

    @Autowired
    private BookIssueRepository bookIssueRepository;


    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

    public  int fine(String id){
        System.out.println("Fine"+id);

        int fine = 0;
        BookIssued bookIssued = bookIssueRepository.findById(id).get();
        String issuedDate = bookIssued.getIssuedDate();
        String CurrentDate= LocalDate.now().format(formatter);
        LocalDate localDate = LocalDate.parse(CurrentDate, formatter);
        LocalDate issuedDate1 = LocalDate.parse(issuedDate, formatter);
        int period = Period.between(issuedDate1,localDate).getDays();
        int renewalCount = bookIssued.getRenewalCount();
        if (renewalCount == 0) {
            renewalCount = 1;
        }
        int finePerDay = 10;
        int maxLimitToSubmit=renewalCount*15;
        System.out.println("Max Limit to submit" + maxLimitToSubmit);
        System.out.println("Period" + period);
        if(maxLimitToSubmit<period){
            fine = (period-maxLimitToSubmit) * finePerDay;
            System.out.println("Fine" + fine);
        }else {
            System.out.println("No Fine");
        }
        return fine;
    }
    public String submitDate(String id) {
//        String id = "2d375e11-4eca-401b-9ad5-edd302695862";
        System.out.println("Submit Date "+id);
        BookIssued bookIssued = bookIssueRepository.findById(id).get();
        String issuedDate = bookIssued.getIssuedDate();
        int renewalCount = bookIssued.getRenewalCount();
        if (renewalCount == 0) {
            renewalCount = 1;
        }
        int maxLimitToSubmit = renewalCount * 15;
        LocalDate localDate = LocalDate.parse(issuedDate,formatter);
        LocalDate submitDate = localDate.plusDays(maxLimitToSubmit);
        return submitDate.format(formatter);


    }
}
