5 REM ChangeCalculator in bwbasic
10 PRINT "Cost of product:"
20 INPUT "...(Cost of Product)...", productCost
30 PRINT
40 PRINT "Amount paid:"
50 INPUT "...(Amount Paid)...", amountPaid
60 PRINT
70 IF productCost > amountPaid THEN GOTO 1080
100 PRINT "Cost:"
101 PRINT productCost
105 PRINT "Amount paid:"
106 PRINT amountPaid
107 PRINT
110 PRINT
115 DEFINT b
116 b = 0
120 DEFINT q
121 q = 0
125 DEFINT d
126 d = 0
130 DEFINT n
131 n = 0
135 DEFDBL c
136 c = amountPaid - productCost
140 IF c >= 1 THEN GOTO 650
180 IF c >= .25 THEN GOTO 790
220 IF c >= .1 THEN GOTO 930
260 IF c >= .05 THEN GOTO 970
300 PRINT
310 PRINT "Change given:"
320 PRINT "Dollars:"
330 PRINT b
340 PRINT "Quarters:"
350 PRINT q
360 PRINT "Dimes:"
370 PRINT d
380 PRINT "Nickels:"
390 PRINT n
400 PRINT "Pennies:"
405 c = c * 100
410 PRINT CINT(c)
420 SYSTEM
430 REM END OF PROGRAM


650 b = b + 1
660 c = c - 1
670 GOTO 140

790 q = q + 1
800 c = c - .25
810 GOTO 180

930 d = d + 1
940 c = c - .1
950 GOTO 220


970 n = n + 1
980 c = c - .05
990 GOTO 260

1080 PRINT "Insufficient amount paid. Exiting program."
1085 SYSTEM
1090 REM END OF PROGRAM
