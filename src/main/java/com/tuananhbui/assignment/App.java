package com.tuananhbui.assignment;

import com.tuananhbui.assignment.service.Service;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	Service service = new Service();
    	service.readDataAndSendRequest();
    }
}
