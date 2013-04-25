
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
from itertools import imap

def pearsonr(x, y):
  # Assume len(x) == len(y)
  n = len(x)
  sum_x = float(sum(x))
  sum_y = float(sum(y))
  sum_x_sq = sum(map(lambda x: pow(x, 2), x))
  sum_y_sq = sum(map(lambda x: pow(x, 2), y))
  psum = sum(imap(lambda x, y: x * y, x, y))
  num = psum - (sum_x * sum_y/n)
  den = pow((sum_x_sq - pow(sum_x, 2) / n) * (sum_y_sq - pow(sum_y, 2) / n), 0.5)
  if den == 0: return 0
  return num / den
  
  
def writeDataToFile(yTest, yPred):
        outputFile = open("../files/predictedratings.csv", 'w+')
        rows = len(yPred)
        outputFile.write("Predicted,Actual\n");
        for i in range(0, rows):
            outputFile.write(str(yPred[i]) + "," + str(yTest[i]) + "\n")
        outputFile.close()
        
def writeEvaluationResults(rmse,mae,filename):
    outputFile = open("../files/"+filename+"_new.csv", 'w+')
    outputFile.write("RMSE value :"+str(rmse)+" MAE value : "+str(mae))
    outputFile.close()     
        
def evaluateCorrelationResults(xTrain,yTrain):
    outputFile = open("../files/correlation.csv", 'w+')
    for i in range(1,xTrain.shape[1]):
        correlation = pearsonr(xTrain[:,i],yTrain)
        outputFile.write("i = "+str(i)+" pearson correlation = "+str(correlation)+"\n")
    outputFile.close()             
        
def doLinearReg(xTrain, yTrain, xTest, yTest):    
        linReg = linear_model.LinearRegression();
        # = RFECV(linReg, step=1, cv=5)
        linReg.fit(xTrain, yTrain);
        yPred = linReg.predict(xTest); 
        rmse = mean_absolute_error(yTest, yPred)
        mae = np.sqrt(mean_squared_error(yTest, yPred))
        print "Linear regression MAE = ", rmse;
        print "RMSE(0.0 best score) ",mae; 
        writeEvaluationResults(rmse,mae,"linear_reg");
        return yPred
    
    
def doSVReg(xTrain, yTrain, xTest, yTest):
        linReg = SVR(degree=2,gamma=0.5,probability=True,shrinking=True);
        linReg.fit(xTrain, yTrain);
        yPred = linReg.predict(xTest); 
        rmse = mean_absolute_error(yTest, yPred)
        mae = np.sqrt(mean_squared_error(yTest, yPred))
        print "SVR MAE", rmse;
        print "RMSE(0.0 best score) ",mae; 
        writeEvaluationResults(rmse,mae,"svr");
        return yPred  

def doSVReg1(xTrain, yTrain, xTest, yTest):
        linReg = SVR(degree=6,gamma=0.5,probability=True,shrinking=True);
        linReg.fit(xTrain, yTrain);
        yPred = linReg.predict(xTest); 
        rmse = mean_absolute_error(yTest, yPred)
        mae = np.sqrt(mean_squared_error(yTest, yPred))
        print "SVR1 MAE", rmse;
        print "RMSE(0.0 best score) ",mae; 
        writeEvaluationResults(rmse,mae,"svr1");
        return yPred 
           
def doSVReg2(xTrain, yTrain, xTest, yTest):
        linReg = SVR(degree=2,gamma=0.8,probability=True,shrinking=True);
        linReg.fit(xTrain, yTrain);
        yPred = linReg.predict(xTest); 
        rmse = mean_absolute_error(yTest, yPred)
        mae = np.sqrt(mean_squared_error(yTest, yPred))
        print "SVR2 MAE", rmse;
        print "RMSE(0.0 best score) ",mae; 
        writeEvaluationResults(rmse,mae,"svr2");
        return yPred   

def doSVReg3(xTrain, yTrain, xTest, yTest):
        linReg = SVR(degree=6,gamma=0.8,probability=True,shrinking=True);
        linReg.fit(xTrain, yTrain);
        yPred = linReg.predict(xTest); 
        rmse = mean_absolute_error(yTest, yPred)
        mae = np.sqrt(mean_squared_error(yTest, yPred))
        print "SVR3 MAE", rmse;
        print "RMSE(0.0 best score) ",mae; 
        writeEvaluationResults(rmse,mae,"svr3");
        return yPred

def doSVReg4(xTrain, yTrain, xTest, yTest):
        linReg = SVR(degree=2,gamma=0.2,probability=True,shrinking=True);
        linReg.fit(xTrain, yTrain);
        yPred = linReg.predict(xTest); 
        rmse = mean_absolute_error(yTest, yPred)
        mae = np.sqrt(mean_squared_error(yTest, yPred))
        print "SVR4 MAE", rmse;
        print "RMSE(0.0 best score) ",mae; 
        writeEvaluationResults(rmse,mae,"svr4");
        return yPred                        

def doKernelReg1(xTrain, yTrain, xTest, yTest):
        linReg = NuSVR();
        linReg.fit(xTrain, yTrain);
        yPred = linReg.predict(xTest); 
        rmse = mean_absolute_error(yTest, yPred)
        mae = np.sqrt(mean_squared_error(yTest, yPred))
        print "NuSVR MAE", rmse;
        print "RMSE(0.0 best score) ",mae; 
        writeEvaluationResults(rmse,mae,"nusvr");
        return yPred   


def doKernelReg2(xTrain, yTrain, xTest, yTest):
        linReg = KNeighborsRegressor();
        linReg.fit(xTrain, yTrain);
        yPred = linReg.predict(xTest); 
        rmse = mean_absolute_error(yTest, yPred)
        mae = np.sqrt(mean_squared_error(yTest, yPred))
        print "KNN MAE", rmse;
        print "RMSE(0.0 best score) ",mae; 
        writeEvaluationResults(rmse,mae,"knn");
        return yPred   


def doKernelReg3(xTrain, yTrain, xTest, yTest):
        linReg = NearestNeighbors(radius=2.0);
        linReg.fit(xTrain, yTrain);
        yPred = linReg.predict(xTest); 
        rmse = mean_absolute_error(yTest, yPred)
        mae = np.sqrt(mean_squared_error(yTest, yPred))
        print "NNR MAE", rmse;
        print "RMSE(0.0 best score) ",mae; 
        writeEvaluationResults(rmse,mae,"nnr");
        return yPred   


def doKernelReg4(xTrain, yTrain, xTest, yTest):
        linReg = linear_model.ARDRegression();
        linReg.fit(xTrain, yTrain);
        yPred = linReg.predict(xTest); 
        rmse = mean_absolute_error(yTest, yPred)
        mae = np.sqrt(mean_squared_error(yTest, yPred))
        print "ARD reg MAE", rmse;
        print "RMSE(0.0 best score) ",mae; 
        writeEvaluationResults(rmse,mae,"ard_reg");
        return yPred   

def doKernelReg5(xTrain, yTrain, xTest, yTest):
        linReg = linear_model.SGDRegressor();
        linReg.fit(xTrain, yTrain);
        yPred = linReg.predict(xTest); 
        rmse = mean_absolute_error(yTest, yPred)
        mae = np.sqrt(mean_squared_error(yTest, yPred))
        print "SGD MAE", rmse;
        print "RMSE(0.0 best score) ",mae; 
        writeEvaluationResults(rmse,mae,"sgd");
        return yPred   

def SVC(xTrain, yTrain, xTest, yTest):
    print "SVM";
    svmclf = svm.SVC(C=8.0,gamma=0.10,kernel='rbf',probability=True,shrinking=True);
    svmclf.fit(xTrain,yTrain);
    yPred = svmclf.predict(xTest);
    rmse = mean_absolute_error(yTest, yPred)
    mae = np.sqrt(mean_squared_error(yTest, yPred))
    print "SVM classification MAE", rmse;
    print "RMSE(0.0 best score) ",mae; 
    writeEvaluationResults(rmse,mae,"svm_classify");
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
                    
        xTrain = trainingData[:, 1:trainingData.shape[1]-1]
        yTrain = trainingData[:, trainingData.shape[1] - 1]
        
        xTest = testData[:, 1:]
        yTest = testingLabels
        
        
        
        print "Xtrain shape :", xTrain.shape
        print "Ytrain shape :", yTrain.shape
        print "Xtest shape :", xTest.shape
        print "Ytest shape :", yTest.shape
        
        evaluateCorrelationResults(xTrain,yTrain)
        
        #yPred_lr = doLinearReg(xTrain, yTrain, xTest, yTest)
        yPred_sg = doSVReg(xTrain, yTrain, xTest, yTest)
        doSVReg1(xTrain, yTrain, xTest, yTest)
        doSVReg2(xTrain, yTrain, xTest, yTest)
        doSVReg3(xTrain, yTrain, xTest, yTest)
        doSVReg4(xTrain, yTrain, xTest, yTest)       
        #doKernelReg5(xTrain, yTrain, xTest, yTest)
        #SVC(xTrain, yTrain, xTest, yTest);
        
        
       
        
        writeDataToFile(yTest, yPred_sg);
                           
if __name__ == '__main__':
    if len(sys.argv) < 3:       
        print 'python datamodeller.py <training-file-path>  <test-file-path> <test_label,0 to read from test file>'
        sys.exit(1)
    training_file = sys.argv[1]
    test_file = sys.argv[2]
    test_label = sys.argv[3]
    model = DataModeller(training_file, test_file, test_label)
    model.runAnalysis()
        
        
