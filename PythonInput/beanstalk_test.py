import beanstalkc
beanstalk = beanstalkc.Connection("beanstalk", parse_yaml=False)
beanstalk.use("new_work")
beanstalk.put('{' + '"jobBool": true,' + '"path": "/mnt/TestPDF.pdf",' + '"outPath": "/mnt/FinalPDF.pdf",'+'"pageLength": 9,'+'"startPage": 1,'+ '"endPage": 9,'+   '"wid": 1019,'+  '"jid": 1109,'+   '"status": "done"' + '}');

#beanstalk.watch()
#beanstalk.put()
#job = beanstalk.reserve()
#job.body
#job.delete()
