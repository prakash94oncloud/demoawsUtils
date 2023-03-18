package hello;

import java.util.List;

import org.joda.time.LocalTime;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.util.EC2MetadataUtils;

public class HelloWorld {
	public static void main(String[] args) {
		Greeter greeter = new Greeter();
		System.out.println(greeter.sayHello());
		
		String instanceId = EC2MetadataUtils.getInstanceId();
		LocalTime currentTime = new LocalTime();
		System.out.println("The current local time is: " + currentTime);
		 
		// Getting EC2 private IP
		String privateIP = EC2MetadataUtils.getInstanceInfo().getPrivateIp();
		System.out.println("Private IP:"+ privateIP);
		
		AmazonEC2 awsEC2client = AmazonEC2ClientBuilder.defaultClient();
		String publicIP = awsEC2client.describeInstances(new DescribeInstancesRequest()
		                     .withInstanceIds(instanceId))
		                        .getReservations()
		                        .stream()
		                        .map(Reservation::getInstances)
		                        .flatMap(List::stream)
		                        .findFirst()
		                        .map(Instance::getPublicIpAddress)
		                        .orElse(null);
		
		System.out.println("Public IP:"+ publicIP);
		LocalTime currentTime2 = new LocalTime();
		System.out.println("The current local time is: " + currentTime2);
	}
}
