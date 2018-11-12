dependency <- read.csv(file="./soft spec/Dependency/DependencyData.csv",header=T)
plot(dependency,xlim=c(0,1))
abline(1,-1)