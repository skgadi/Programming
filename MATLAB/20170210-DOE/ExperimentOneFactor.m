function [ y ] = ExperimentOneFactor( x )
%EXPERIMENTONEFACTOR Summary of this function goes here
%   Detailed explanation goes here
y = -0.6*(x-30).^2+250+(rand(size(x))*2-1)*4;

end

