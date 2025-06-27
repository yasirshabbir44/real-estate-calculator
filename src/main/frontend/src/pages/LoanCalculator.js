import React, { useState, useEffect } from 'react';
import { useLocation, Link } from 'react-router-dom';
import axios from 'axios';

const LoanCalculator = () => {
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const propertyIdParam = queryParams.get('propertyId');

  const [properties, setProperties] = useState([]);
  const [selectedPropertyId, setSelectedPropertyId] = useState(propertyIdParam || '');
  const [property, setProperty] = useState(null);
  const [loanParams, setLoanParams] = useState({
    downPayment: '',
    interestRate: 3.5,
    tenureYears: 25
  });
  const [loanCalculation, setLoanCalculation] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  // Fetch all properties for dropdown selection
  useEffect(() => {
    const fetchProperties = async () => {
      try {
        const response = await axios.get('/api/properties');
        setProperties(response.data);
      } catch (err) {
        console.error('Error fetching properties:', err);
        setError('Failed to load properties. Please try again later.');
      }
    };

    fetchProperties();
  }, []);

  // Fetch property details when ID is selected
  useEffect(() => {
    const fetchPropertyDetails = async () => {
      if (!selectedPropertyId) {
        setProperty(null);
        return;
      }
      
      try {
        const response = await axios.get(`/api/properties/${selectedPropertyId}`);
        setProperty(response.data);
        
        // Set default down payment to 20% of property price
        setLoanParams(prev => ({
          ...prev,
          downPayment: Math.round(response.data.price * 0.2)
        }));
      } catch (err) {
        console.error('Error fetching property details:', err);
        setError('Failed to load property details. Please try again later.');
      }
    };

    fetchPropertyDetails();
  }, [selectedPropertyId]);

  const handlePropertyChange = (e) => {
    setSelectedPropertyId(e.target.value);
    setLoanCalculation(null);
  };

  const handleParamChange = (e) => {
    const { name, value } = e.target;
    setLoanParams({
      ...loanParams,
      [name]: value
    });
  };

  const calculateLoan = async (e) => {
    e.preventDefault();
    
    if (!selectedPropertyId) {
      setError('Please select a property.');
      return;
    }

    if (!loanParams.downPayment || !loanParams.interestRate || !loanParams.tenureYears) {
      setError('Please fill in all loan parameters.');
      return;
    }

    try {
      setLoading(true);
      setError(null);
      
      const response = await axios.post('/api/loan-calculations/calculate', null, {
        params: {
          propertyId: selectedPropertyId,
          downPayment: loanParams.downPayment,
          interestRate: loanParams.interestRate,
          tenureYears: loanParams.tenureYears
        }
      });
      
      setLoanCalculation(response.data);
    } catch (err) {
      console.error('Error calculating loan:', err);
      setError('Failed to calculate loan. Please try again later.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <h1 className="text-3xl font-bold mb-8">Mortgage Calculator</h1>
      
      {/* Loan Calculator Form */}
      <div className="bg-white rounded-lg shadow-md p-6 mb-8">
        <h2 className="text-xl font-semibold mb-4">Calculate Your Mortgage</h2>
        
        <form onSubmit={calculateLoan}>
          <div className="mb-6">
            <label htmlFor="propertyId" className="form-label">Select Property</label>
            <select
              id="propertyId"
              value={selectedPropertyId}
              onChange={handlePropertyChange}
              className="form-input"
              required
            >
              <option value="">Select a property</option>
              {properties.map(property => (
                <option key={property.id} value={property.id}>
                  {property.name} - {property.location} (AED {property.price.toLocaleString()})
                </option>
              ))}
            </select>
          </div>
          
          {property && (
            <>
              <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-6">
                <div>
                  <label htmlFor="downPayment" className="form-label">Down Payment (AED)</label>
                  <input
                    type="number"
                    id="downPayment"
                    name="downPayment"
                    value={loanParams.downPayment}
                    onChange={handleParamChange}
                    className="form-input"
                    min="0"
                    max={property.price}
                    required
                  />
                  <div className="text-sm text-gray-500 mt-1">
                    {loanParams.downPayment && property.price
                      ? `${((loanParams.downPayment / property.price) * 100).toFixed(1)}% of property price`
                      : ''}
                  </div>
                </div>
                
                <div>
                  <label htmlFor="interestRate" className="form-label">Interest Rate (%)</label>
                  <input
                    type="number"
                    id="interestRate"
                    name="interestRate"
                    value={loanParams.interestRate}
                    onChange={handleParamChange}
                    className="form-input"
                    min="0"
                    max="20"
                    step="0.1"
                    required
                  />
                </div>
                
                <div>
                  <label htmlFor="tenureYears" className="form-label">Loan Tenure (Years)</label>
                  <input
                    type="number"
                    id="tenureYears"
                    name="tenureYears"
                    value={loanParams.tenureYears}
                    onChange={handleParamChange}
                    className="form-input"
                    min="1"
                    max="35"
                    required
                  />
                </div>
              </div>
              
              <div className="flex items-center justify-between bg-gray-100 p-4 rounded-lg mb-6">
                <div>
                  <span className="block text-sm text-gray-600">Property Price</span>
                  <span className="text-xl font-semibold">AED {property.price.toLocaleString()}</span>
                </div>
                <div>
                  <span className="block text-sm text-gray-600">Loan Amount</span>
                  <span className="text-xl font-semibold">
                    AED {loanParams.downPayment ? (property.price - loanParams.downPayment).toLocaleString() : '-'}
                  </span>
                </div>
              </div>
              
              <button 
                type="submit" 
                className="btn btn-primary"
                disabled={loading}
              >
                {loading ? 'Calculating...' : 'Calculate Mortgage'}
              </button>
            </>
          )}
        </form>
      </div>
      
      {/* Error Message */}
      {error && (
        <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-6">
          {error}
        </div>
      )}
      
      {/* Loading Indicator */}
      {loading && (
        <div className="flex justify-center items-center py-12">
          <div className="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-primary"></div>
        </div>
      )}
      
      {/* Loan Calculation Results */}
      {loanCalculation && (
        <div className="bg-white rounded-lg shadow-md p-6 mb-8">
          <h2 className="text-2xl font-semibold mb-6">Mortgage Calculation Results</h2>
          
          <div className="grid grid-cols-1 md:grid-cols-3 gap-8 mb-8">
            <div className="bg-primary-light rounded-lg p-6 text-center">
              <h3 className="text-lg font-semibold mb-2">Monthly Payment</h3>
              <div className="text-3xl font-bold">AED {loanCalculation.monthlyEmi.toLocaleString(undefined, { maximumFractionDigits: 0 })}</div>
            </div>
            
            <div className="bg-gray-100 rounded-lg p-6 text-center">
              <h3 className="text-lg font-semibold mb-2">Total Interest</h3>
              <div className="text-3xl font-bold">AED {loanCalculation.totalInterest.toLocaleString(undefined, { maximumFractionDigits: 0 })}</div>
            </div>
            
            <div className="bg-gray-100 rounded-lg p-6 text-center">
              <h3 className="text-lg font-semibold mb-2">Total Amount Payable</h3>
              <div className="text-3xl font-bold">AED {loanCalculation.totalPayable.toLocaleString(undefined, { maximumFractionDigits: 0 })}</div>
            </div>
          </div>
          
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-8">
            <div>
              <h3 className="text-lg font-semibold mb-4">Loan Details</h3>
              <table className="w-full">
                <tbody>
                  <tr className="border-b">
                    <td className="py-2 text-gray-600">Property Price</td>
                    <td className="py-2 text-right font-medium">AED {property.price.toLocaleString()}</td>
                  </tr>
                  <tr className="border-b">
                    <td className="py-2 text-gray-600">Down Payment</td>
                    <td className="py-2 text-right font-medium">AED {loanCalculation.downPayment.toLocaleString()}</td>
                  </tr>
                  <tr className="border-b">
                    <td className="py-2 text-gray-600">Loan Amount</td>
                    <td className="py-2 text-right font-medium">AED {loanCalculation.loanAmount.toLocaleString()}</td>
                  </tr>
                  <tr className="border-b">
                    <td className="py-2 text-gray-600">Interest Rate</td>
                    <td className="py-2 text-right font-medium">{loanCalculation.interestRate}%</td>
                  </tr>
                  <tr className="border-b">
                    <td className="py-2 text-gray-600">Loan Tenure</td>
                    <td className="py-2 text-right font-medium">{loanCalculation.tenureYears} years</td>
                  </tr>
                  <tr>
                    <td className="py-2 text-gray-600">Loan to Value Ratio</td>
                    <td className="py-2 text-right font-medium">{loanCalculation.loanToValueRatio.toFixed(1)}%</td>
                  </tr>
                </tbody>
              </table>
            </div>
            
            <div>
              <h3 className="text-lg font-semibold mb-4">Payment Breakdown</h3>
              <table className="w-full">
                <tbody>
                  <tr className="border-b">
                    <td className="py-2 text-gray-600">First Month Principal</td>
                    <td className="py-2 text-right font-medium">AED {loanCalculation.firstEmiPrincipal.toLocaleString(undefined, { maximumFractionDigits: 0 })}</td>
                  </tr>
                  <tr className="border-b">
                    <td className="py-2 text-gray-600">First Month Interest</td>
                    <td className="py-2 text-right font-medium">AED {loanCalculation.firstEmiInterest.toLocaleString(undefined, { maximumFractionDigits: 0 })}</td>
                  </tr>
                  <tr className="border-b">
                    <td className="py-2 text-gray-600">Last Month Principal</td>
                    <td className="py-2 text-right font-medium">AED {loanCalculation.lastEmiPrincipal.toLocaleString(undefined, { maximumFractionDigits: 0 })}</td>
                  </tr>
                  <tr className="border-b">
                    <td className="py-2 text-gray-600">Last Month Interest</td>
                    <td className="py-2 text-right font-medium">AED {loanCalculation.lastEmiInterest.toLocaleString(undefined, { maximumFractionDigits: 0 })}</td>
                  </tr>
                  <tr className="border-b">
                    <td className="py-2 text-gray-600">Total Principal</td>
                    <td className="py-2 text-right font-medium">AED {loanCalculation.loanAmount.toLocaleString()}</td>
                  </tr>
                  <tr>
                    <td className="py-2 text-gray-600">Total Interest</td>
                    <td className="py-2 text-right font-medium">AED {loanCalculation.totalInterest.toLocaleString(undefined, { maximumFractionDigits: 0 })}</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
          
          <div className="flex justify-between">
            <Link to={`/properties/${property.id}`} className="text-primary hover:underline">
              Back to Property Details
            </Link>
            <Link to={`/cost-breakdown?propertyId=${property.id}`} className="text-primary hover:underline">
              View Cost Breakdown
            </Link>
          </div>
        </div>
      )}
    </div>
  );
};

export default LoanCalculator;