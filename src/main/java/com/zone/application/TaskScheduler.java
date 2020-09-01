package com.zone.application;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TaskScheduler {

    private static final Logger logger = LoggerFactory.getLogger(TaskScheduler.class);
    private final Map<String, Scheduler> taskMap = new HashMap<>();
	private final StdSchedulerFactory factory = new StdSchedulerFactory();
	private static TaskScheduler scheduler;

	private TaskScheduler() {
	}

	public static TaskScheduler getInstance() {
		if (scheduler == null) {
			scheduler = new TaskScheduler();
		}
		return scheduler;
	}

	public TaskScheduler addJob(Class<? extends Job> taskClass, String triggerExpression) {
		String className = taskClass.getSimpleName();
		addJob(taskClass, className + "Task", className + "Group", className + "Trigger",
				triggerExpression);
		return scheduler;
	}

	/**
     * 添加任务
     * @param taskClass 执行任务
     * @param taskName 任务名称
     * @param triggerExpression 任务执行规则
	 * @param triggerName 触发器名
	 *
     */
    public void addJob(Class<? extends Job> taskClass, String taskName, String group,
                       String triggerName,
                       String triggerExpression) {
        try {
            Scheduler scheduler = factory.getScheduler();
            JobDetail job = JobBuilder.newJob(taskClass)
                        .withIdentity(taskName, group)
                        .build();
            CronTrigger trigger = TriggerBuilder.newTrigger()
                                    .withIdentity(triggerName, group)
                                    .withSchedule(CronScheduleBuilder.cronSchedule(triggerExpression))
                                    .build();
            Date date = scheduler.scheduleJob(job, trigger);
            logger.info("任务:{},首次安排于{}执行，并重复以下规则:{}",
					taskName, date, trigger.getCronExpression());
            taskMap.put(taskName, scheduler);
        } catch (SchedulerException e) {
            logger.error("安排任务失败:{}", taskName, e);
        }
    }


	public void startTask() {
		try {
			for (Map.Entry<String, Scheduler> entry: taskMap.entrySet()){
				entry.getValue().start();
			}
		} catch (SchedulerException e) {
			logger.error("启动任务失败", e);
		}
	}
}
