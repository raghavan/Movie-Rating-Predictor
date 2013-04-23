
import numpy as np
import scipy
import sys
from sklearn import linear_model, svm, naive_bayes
from scipy.io import arff
import pylab as pl
import pickle
from sklearn.metrics import mean_squared_error, mean_absolute_error
from sklearn.feature_selection import RFECV
from sklearn.svm import SVR
from sklearn.svm import NuSVR
from sklearn.neighbors import KNeighborsRegressor
from sklearn.neighbors import NearestNeighbors

def writeDataToFile(yTest, yPred):
        outputFile = open("../files/predictedratings.csv", 'w+')
        rows = len(yPred)
        outputFile.write("Predicted,Actual\n");
        for i in range(0, rows):
            outputFile.write(str(yPred[i]) + "," + str(yTest[i]) + "\n")
        outputFile.close()
        
def doLinearReg(xTrain, yTrain, xTest, yTest):
        linReg = linear_model.LinearRegression();
        # = RFECV(linReg, step=1, cv=5)
        linReg.fit(xTrain, yTrain);
        yPred = linReg.predict(xTest); 
        print "Linear regression MAE = ", mean_absolute_error(yTest, yPred);
        print "RMSE(0.0 best score) ", np.sqrt(mean_squared_error(yTest, yPred))
        return yPred
    
def doSVReg(xTrain, yTrain, xTest, yTest):
        linReg = SVR();
        linReg.fit(xTrain, yTrain);
        yPred = linReg.predict(xTest); 
        print "SVR MAE = ", mean_absolute_error(yTest, yPred);
        print "RMSE(0.0 best score) ", np.sqrt(mean_squared_error(yTest, yPred));
        return yPred    

def doKernelReg1(xTrain, yTrain, xTest, yTest):
        linReg = NuSVR();
        linReg.fit(xTrain, yTrain);
        yPred = linReg.predict(xTest); 
        print "NuSVR regression MAE = ", mean_absolute_error(yTest, yPred);
        print "RMSE(0.0 best score) ", np.sqrt(mean_squared_error(yTest, yPred));
        return yPred   


def doKernelReg2(xTrain, yTrain, xTest, yTest):
        linReg = KNeighborsRegressor();
        linReg.fit(xTrain, yTrain);
        yPred = linReg.predict(xTest); 
        print "KNeighborsRegressor regression MAE = ", mean_absolute_error(yTest, yPred);
        print "RMSE(0.0 best score) ", np.sqrt(mean_squared_error(yTest, yPred));
        return yPred   


def doKernelReg3(xTrain, yTrain, xTest, yTest):
        linReg = NearestNeighbors(radius=2.0);
        linReg.fit(xTrain, yTrain);
        yPred = linReg.predict(xTest); 
        print "NearestNeighbors regression MAE = ", mean_absolute_error(yTest, yPred);
        print "RMSE(0.0 best score) ", np.sqrt(mean_squared_error(yTest, yPred));
        return yPred   


def doKernelReg4(xTrain, yTrain, xTest, yTest):
        linReg = linear_model.ARDRegression();
        linReg.fit(xTrain, yTrain);
        yPred = linReg.predict(xTest); 
        print "ARDRegression regression MAE = ", mean_absolute_error(yTest, yPred);
        print "RMSE(0.0 best score) ", np.sqrt(mean_squared_error(yTest, yPred));
        return yPred   

def doKernelReg5(xTrain, yTrain, xTest, yTest):
        linReg = linear_model.SGDRegressor();
        linReg.fit(xTrain, yTrain);
        yPred = linReg.predict(xTest); 
        print "SGDRegressor MAE = ", mean_absolute_error(yTest, yPred);
        print "RMSE(0.0 best score) ", np.sqrt(mean_squared_error(yTest, yPred));
        return yPred   
        

        
class DataModeller:
    
    def __init__(self, training_file, test_file, test_label_file):
        self.training_file = training_file
        self.test_file = test_file
        self.test_label_file = test_label_file
        
    def runAnalysis(self):
        
        trainingData = np.loadtxt(open(self.training_file, 'rb'), delimiter=';')
        testData = np.loadtxt(open(self.test_file, 'rb'), delimiter=';')
        testingLabels = np.loadtxt(open(self.test_label_file, 'rb'), delimiter=';')
                    
        xTrain = trainingData[:, 2:trainingData.shape[1]-1]
        yTrain = trainingData[:, trainingData.shape[1] - 1]
        
        xTest = testData[:, 2:]
        yTest = testingLabels
        
        
        print "Xtrain shape :", xTrain.shape
        print "Ytrain shape :", yTrain.shape
        print "Xtest shape :", xTest.shape
        print "Ytest shape :", yTest.shape
        
        yPred_lr = doLinearReg(xTrain, yTrain, xTest, yTest)
        yPred_sg = doSVReg(xTrain, yTrain, xTest, yTest)
       
        doKernelReg5(xTrain, yTrain, xTest, yTest)
       
        
        writeDataToFile(yTest, yPred_lr);
                           
if __name__ == '__main__':
    if len(sys.argv) < 3:       
        print 'python datamodeller.py <training-file-path>  <test-file-path> <test_label,0 to read from test file>'
        sys.exit(1)
    training_file = sys.argv[1]
    test_file = sys.argv[2]
    test_label = sys.argv[3]
    model = DataModeller(training_file, test_file, test_label)
    model.runAnalysis()
        
        
