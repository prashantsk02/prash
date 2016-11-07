# =================================
# Task -- Web crawler
# =================================

Your task is to write a simple web crawler in a language of your choice.

The crawler should be limited to one domain. 
Given a starting URL – say wiprodigital.com - it should visit all pages within the domain, but not follow the links to external sites such as Google or Twitter.
The output should be a simple site map, showing links to other pages under the same domain, links to static content such as images, and to external URLs.
We would like to see what you can produce in a couple of hours – please don’t spend much more than that. In addition, please
·         ensure that what you do implement is production quality code
·         briefly describe any tradeoffs you make through comments and / or in a README file
·         include the steps needed to build and run your solution

Once done, please make your solution available on Github and forward the link.

## Description
This webcrawler is created using Jsoup.

## Usage

    $ java -jar target/web-crawler-1.0-SNAPSHOT.jar "http://wiprodigital.com/"
	
	OR, Even without anyput the default url will be taken as http://wiprodigital.com/
		
## Example output XML file format

<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<sitemap>
	<externalLink>
		<url>https://www.linkedin.com/company/wipro-digitals</url>
	</externalLink>
	<internalLink>
		<url>http://wiprodigital.com/2016/08/31/the-digital-economy-is-dependent-on-data-sharing-security-and-trust/</url>
	</internalLink>
	<internalLink>
		<url>http://wiprodigital.com/get-in-touch</url>
	</internalLink>
	<internalLink>
		<url>http://wiprodigital.com/news/a-new-way-of-working-by-pierre-audoin-consultants-pac/</url>
	</internalLink>
	<imagesLink>
		<url>http://17776-presscdn-0-6.pagely.netdna-cdn.com/wp-content/themes/wiprodigital/images/wdlogo.png</url>
	</imagesLink>
	<imagesLink>
		<url>http://17776-presscdn-0-6.pagely.netdna-cdn.com/wp-content/themes/wiprodigital/images/wdlogo.png</url>
	</imagesLink>
</sitemap>

## Example console log output

$ java -jar ./prashantWebCrawler.jar

Received page : http://wiprodigital.com/

 Title : Digital Transformation - Wipro Digital
Internal Link : http://wiprodigital.com,  Data :
Internal Link : http://wiprodigital.com/who-we-are,  Data :Who we are
Internal Link : http://wiprodigital.com/who-we-are,  Data :Our story
Internal Link : http://wiprodigital.com/who-we-are#wdteam_meetus,  Data :Meet us
Internal Link : http://wiprodigital.com/who-we-are#wdteam_leaders,  Data :Leaders
Image Link : http://17776-presscdn-0-6.pagely.netdna-cdn.com/wp-content/themes/wiprodigital/images/wdlogo.png , alt : wipro digital
Image Link : http://17776-presscdn-0-6.pagely.netdna-cdn.com/wp-content/themes/wiprodigital/images/wdlogo.png , alt : Digital Transformation - Wipro Digital

D:\xxxx\xxxxx\webCrawler\sitemap.xml is created.
