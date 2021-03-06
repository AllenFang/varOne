/**
 * 
 */
package com.haredb.sparkmonitor.yarn.service;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.hadoop.yarn.api.records.ApplicationReport;
import org.apache.hadoop.yarn.api.records.NodeReport;
import org.apache.hadoop.yarn.api.records.YarnApplicationState;
import org.apache.hadoop.yarn.client.api.YarnClient;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.exceptions.YarnException;

/**
 * @author allen
 *
 */
public class YarnService implements Closeable{
	
	private YarnClient yarnClient;
	
	
	public YarnService(Configuration config){
		YarnConfiguration conf = new YarnConfiguration(config);
		this.yarnClient = YarnClient.createYarnClient();
		this.yarnClient.init(conf);
		this.yarnClient.start();
	}
	
	public List<String> getAllNodeHost() throws YarnException, IOException{
		List<NodeReport> nodeReports = this.yarnClient.getNodeReports();
		List<String> hosts = new ArrayList<String>(nodeReports.size());
		for(NodeReport nr: nodeReports){
//			if(nr.getHttpAddress().split(":")[0].equals("server-a1")) continue;
			hosts.add(nr.getHttpAddress().split(":")[0]);
		}
		return hosts;
	}
	
	public List<String> getRunningSparkApplications() throws YarnException, IOException{
		List<String> applicationIds = new ArrayList<String>();
		for(ApplicationReport report : this.yarnClient.getApplications()){
			if(report.getApplicationType().equals("SPARK") && report.getYarnApplicationState().name().equals("RUNNING")){
				applicationIds.add(report.getApplicationId().toString());
			}
		}
		return applicationIds;
	}
	
	public boolean isStartRunningSparkApplication(String applicationId) throws YarnException, IOException{
		boolean valid = false;
		for(ApplicationReport report : this.yarnClient.getApplications()){
			if(report.getApplicationType().equals("SPARK") && report.getApplicationId().toString().equals(applicationId)){
				if(report.getYarnApplicationState().equals(YarnApplicationState.RUNNING) || 
						report.getYarnApplicationState().equals(YarnApplicationState.FINISHED)){
					valid = true;
				}
				break;
			}
		}
		System.out.println(valid);
		return valid;
	}

	@Override
	public void close() throws IOException {
		this.yarnClient.close();
	}
	
}
