import React, { useState, useEffect } from 'react';

function CostBreakdown() {
  const [propertyPrice, setPropertyPrice] = useState(1000000);
  const [downPayment, setDownPayment] = useState(20);
  const [loanTerm, setLoanTerm] = useState(25);
  const [interestRate, setInterestRate] = useState(3.5);
  const [costs, setCosts] = useState({});

  useEffect(() => {
    calculateCosts();
  }, [propertyPrice, downPayment, loanTerm, interestRate]);

  const calculateCosts = () => {
    // Calculate down payment amount
    const downPaymentAmount = (propertyPrice * downPayment) / 100;
    
    // Calculate loan amount
    const loanAmount = propertyPrice - downPaymentAmount;
    
    // Calculate registration fee (4% of property price)
    const registrationFee = propertyPrice * 0.04;
    
    // Calculate transfer fee (2% of property price)
    const transferFee = propertyPrice * 0.02;
    
    // Calculate real estate agency fee (2% of property price)
    const agencyFee = propertyPrice * 0.02;
    
    // Calculate mortgage registration fee (0.25% of loan amount)
    const mortgageRegistrationFee = loanAmount * 0.0025;
    
    // Calculate valuation fee (fixed)
    const valuationFee = 3000;
    
    // Calculate mortgage processing fee (1% of loan amount, capped at 10,000)
    const mortgageProcessingFee = Math.min(loanAmount * 0.01, 10000);
    
    // Calculate title deed fee (fixed)
    const titleDeedFee = 580;
    
    // Calculate monthly mortgage payment
    const monthlyInterestRate = interestRate / 100 / 12;
    const numberOfPayments = loanTerm * 12;
    const monthlyPayment = loanAmount * 
      (monthlyInterestRate * Math.pow(1 + monthlyInterestRate, numberOfPayments)) / 
      (Math.pow(1 + monthlyInterestRate, numberOfPayments) - 1);
    
    // Calculate total interest paid
    const totalInterestPaid = (monthlyPayment * numberOfPayments) - loanAmount;
    
    // Calculate total cost of ownership
    const totalUpfrontCosts = downPaymentAmount + registrationFee + transferFee + 
      agencyFee + mortgageRegistrationFee + valuationFee + 
      mortgageProcessingFee + titleDeedFee;
    
    const totalCostOfOwnership = totalUpfrontCosts + loanAmount + totalInterestPaid;
    
    setCosts({
      downPaymentAmount,
      loanAmount,
      registrationFee,
      transferFee,
      agencyFee,
      mortgageRegistrationFee,
      valuationFee,
      mortgageProcessingFee,
      titleDeedFee,
      monthlyPayment,
      totalInterestPaid,
      totalUpfrontCosts,
      totalCostOfOwnership
    });
  };

  // Format currency
  const formatCurrency = (amount) => {
    return new Intl.NumberFormat('en-AE', {
      style: 'currency',
      currency: 'AED',
      minimumFractionDigits: 0,
      maximumFractionDigits: 0
    }).format(amount);
  };

  return (
    <div className="max-w-4xl mx-auto">
      <h1 className="text-3xl font-bold mb-6">Property Cost Breakdown</h1>
      <p className="mb-6">
        Understand all the costs associated with purchasing a property in Dubai, 
        including fees, taxes, and financing costs.
      </p>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
        <div className="md:col-span-1 bg-white p-6 rounded-lg shadow-md">
          <h2 className="text-xl font-semibold mb-4">Property Details</h2>
          
          <div className="mb-4">
            <label className="block text-gray-700 mb-2">
              Property Price: {formatCurrency(propertyPrice)}
            </label>
            <input 
              type="range" 
              min="500000" 
              max="10000000" 
              step="100000"
              value={propertyPrice}
              onChange={(e) => setPropertyPrice(Number(e.target.value))}
              className="w-full"
            />
          </div>
          
          <div className="mb-4">
            <label className="block text-gray-700 mb-2">
              Down Payment: {downPayment}%
            </label>
            <input 
              type="range" 
              min="20" 
              max="80" 
              step="5"
              value={downPayment}
              onChange={(e) => setDownPayment(Number(e.target.value))}
              className="w-full"
            />
          </div>
          
          <div className="mb-4">
            <label className="block text-gray-700 mb-2">
              Loan Term: {loanTerm} years
            </label>
            <input 
              type="range" 
              min="5" 
              max="30" 
              step="1"
              value={loanTerm}
              onChange={(e) => setLoanTerm(Number(e.target.value))}
              className="w-full"
            />
          </div>
          
          <div className="mb-4">
            <label className="block text-gray-700 mb-2">
              Interest Rate: {interestRate}%
            </label>
            <input 
              type="range" 
              min="2" 
              max="8" 
              step="0.1"
              value={interestRate}
              onChange={(e) => setInterestRate(Number(e.target.value))}
              className="w-full"
            />
          </div>
        </div>
        
        <div className="md:col-span-2">
          <div className="bg-white p-6 rounded-lg shadow-md mb-6">
            <h2 className="text-xl font-semibold mb-4">Cost Breakdown</h2>
            
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div>
                <h3 className="font-medium text-gray-700 mb-3">Upfront Costs</h3>
                <ul className="space-y-2">
                  <li className="flex justify-between">
                    <span>Down Payment:</span>
                    <span className="font-medium">{formatCurrency(costs.downPaymentAmount || 0)}</span>
                  </li>
                  <li className="flex justify-between">
                    <span>Registration Fee (4%):</span>
                    <span className="font-medium">{formatCurrency(costs.registrationFee || 0)}</span>
                  </li>
                  <li className="flex justify-between">
                    <span>Transfer Fee (2%):</span>
                    <span className="font-medium">{formatCurrency(costs.transferFee || 0)}</span>
                  </li>
                  <li className="flex justify-between">
                    <span>Agency Fee (2%):</span>
                    <span className="font-medium">{formatCurrency(costs.agencyFee || 0)}</span>
                  </li>
                  <li className="flex justify-between">
                    <span>Mortgage Registration (0.25%):</span>
                    <span className="font-medium">{formatCurrency(costs.mortgageRegistrationFee || 0)}</span>
                  </li>
                  <li className="flex justify-between">
                    <span>Valuation Fee:</span>
                    <span className="font-medium">{formatCurrency(costs.valuationFee || 0)}</span>
                  </li>
                  <li className="flex justify-between">
                    <span>Mortgage Processing Fee:</span>
                    <span className="font-medium">{formatCurrency(costs.mortgageProcessingFee || 0)}</span>
                  </li>
                  <li className="flex justify-between">
                    <span>Title Deed Fee:</span>
                    <span className="font-medium">{formatCurrency(costs.titleDeedFee || 0)}</span>
                  </li>
                </ul>
              </div>
              
              <div>
                <h3 className="font-medium text-gray-700 mb-3">Financing Costs</h3>
                <ul className="space-y-2">
                  <li className="flex justify-between">
                    <span>Loan Amount:</span>
                    <span className="font-medium">{formatCurrency(costs.loanAmount || 0)}</span>
                  </li>
                  <li className="flex justify-between">
                    <span>Monthly Payment:</span>
                    <span className="font-medium">{formatCurrency(costs.monthlyPayment || 0)}</span>
                  </li>
                  <li className="flex justify-between">
                    <span>Total Interest Paid:</span>
                    <span className="font-medium">{formatCurrency(costs.totalInterestPaid || 0)}</span>
                  </li>
                </ul>
              </div>
            </div>
            
            <div className="mt-6 pt-4 border-t border-gray-200">
              <h3 className="font-medium text-gray-700 mb-3">Summary</h3>
              <ul className="space-y-2">
                <li className="flex justify-between">
                  <span>Total Upfront Costs:</span>
                  <span className="font-semibold">{formatCurrency(costs.totalUpfrontCosts || 0)}</span>
                </li>
                <li className="flex justify-between">
                  <span>Total Cost of Ownership:</span>
                  <span className="font-semibold">{formatCurrency(costs.totalCostOfOwnership || 0)}</span>
                </li>
              </ul>
            </div>
          </div>
          
          <div className="bg-blue-50 p-4 rounded-lg">
            <h3 className="text-lg font-semibold text-blue-800 mb-2">Need Financial Advice?</h3>
            <p className="text-blue-700">
              Our mortgage specialists can help you find the best financing options for your property purchase.
              Contact us at <a href="mailto:finance@realestate.com" className="underline">finance@realestate.com</a> to schedule a consultation.
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}

export default CostBreakdown;