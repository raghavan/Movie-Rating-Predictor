
import numpy as np
import scipy
import sys
from sklearn import linear_model,svm,naive_bayes
from scipy.io import arff
import pylab as pl
import pickle
from sklearn.metrics import confusion_matrix
from sklearn.metrics import precision_recall_fscore_support

class DataModeller:
    
    def __init__(self, training_file, test_file):
        self.training_file = training_file
        self.test_file = test_file
        
    def runAnalysis(self):
        
        trainingData = np.loadtxt(open(self.training_file, 'rb'), delimiter = ',', skiprows = 0, usecols = (1,2,3,4,5,6,7,8,9,10,11) )
        testData = np.loadtxt(open(self.test_file,'rb'), delimiter = ',', skiprows = 0, usecols = (1,2,3,4,5,6,7,8,9,10,11))
        
        xTrain =  trainingData[:,0:10]
        yTrain = trainingData[:,10]

        xTest =  testData[:,0:10]
        yTest = testData[:,10]
                
        linReg = linear_model.LinearRegression();
        linReg.fit(xTrain, yTrain);
        yPred = linReg.predict(xTest); 
        
        for i in range(0,len(yPred)):
            print str(yPred[i]) +"    "+ str(yTest[i]);
                   
        score =  linReg.score(xTest,yTest);
        print "Linear regression score(1.0 is best) = ",score;
        
        errorRateLin = np.sqrt(np.mean(np.power(yPred - yTest,2)))
        print "RMSE(Shows the deviation from 0) "+str(errorRateLin)
        
        
                    
        
        
if __name__ == '__main__':
    if len(sys.argv) < 2:       
        print 'python datamodeller.py <training-file-path>  <test-file-path>'
        sys.exit(1)
    training_file = sys.argv[1]
    test_file = sys.argv[2]
    model = DataModeller(training_file, test_file)
    model.runAnalysis()
        
        
