
import numpy as np
import scipy
import sys
from sklearn import linear_model,svm,naive_bayes
from scipy.io import arff
import pylab as pl
import pickle
from sklearn.metrics import mean_squared_error

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
        linReg.fit(xTrain, yTrain,n_jobs=-1);
        yPred = linReg.predict(xTest); 
            
        outputFile = open("../files/predictedratings.csv", 'w+')
        rows = len(yPred)
        outputFile.write("Predicted,Actual\n");
        for i in range(0,rows):
            outputFile.write(str(yPred[i]) +","+ str(yTest[i])+"\n")
        outputFile.close()
                   
        score =  linReg.score(xTest,yTest);
        print "Linear regression score(1.0 is best) = ",score;
                
        print "RMSE(0.0 best score) ",mean_squared_error(yTest,yPred);
        
        
                           
if __name__ == '__main__':
    if len(sys.argv) < 2:       
        print 'python datamodeller.py <training-file-path>  <test-file-path>'
        sys.exit(1)
    training_file = sys.argv[1]
    test_file = sys.argv[2]
    model = DataModeller(training_file, test_file)
    model.runAnalysis()
        
        
