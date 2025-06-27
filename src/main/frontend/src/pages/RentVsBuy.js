import React, { useState, useEffect } from 'react';
import { useLocation, Link } from 'react-router-dom';
import axios from 'axios';

const RentVsBuy = () => {
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const propertyIdParam = queryParams.get('propertyId');

  const [properties, setProperties] = useState([]);
  const [selectedPropertyId, setSelectedPropertyId] = useState(propertyIdParam || '');
  const [property, setProperty] = useState(null);
  const [analysisParams, setAnalysisParams] = useState({
    downPayment: '',
    interestRate: 3.5,
    loanTenureYears: 25,
    propertyAppreciationRate: 3.0,
    annualMaintenanceCost: 5000,
    annualPropertyTax: 2000,
    monthlyRent: '',
    annualRentIncreaseRate: 5.0,
    securityDeposit: '',
    investmentReturnRate: 7.0,
    analysisPeriodYears: 10
  });
  const [analysis, setAnalysis] = useState(null);
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
        setAnalysisParams(prev => ({
          ...prev,
          downPayment: Math.round(response.data.price * 0.2),
          // Set default monthly rent to 0.5% of property price
          monthlyRent: Math.round(response.data.price * 0.005),
          // Set default security deposit to 2 months rent
          securityDeposit: Math.round(response.data.price * 0.005 * 2)
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
    setAnalysis(null);
  };

  const handleParamChange = (e) => {
    const { name, value } = e.target;
    setAnalysisParams({
      ...analysisParams,
      [name]: value
    });
  };

  const calculateAnalysis = async (e) => {
    e.preventDefault();
    
    if (!selectedPropertyId) {
      setError('Please select a property.');
      return;
    }

    // Validate all required fields are filled
    const requiredFields = [
      'downPayment', 'interestRate', 'loanTenureYears', 'propertyAppreciationRate',
      'annualMaintenanceCost', 'annualPropertyTax', 'monthlyRent', 'annualRentIncreaseRate',
      'securityDeposit', 'investmentReturnRate', 'analysisPeriodYears'
    ];
    
    const missingFields = requiredFields.filter(field => !analysisParams[field] && analysisParams[field] !== 0);
    if (missingFields.length > 0) {
      setError(`Please fill in all required fields: ${missingFields.join(', ')}`);
      return;
    }

    try {
      setLoading(true);
      setError(null);
      
      const response = await axios.post('/api/rent-vs-buy-analyses/calculate', null, {
        params: {
          propertyId: selectedPropertyId,
          ...analysisParams
        }
      });
      
      setAnalysis(response.data);
    } catch (err) {
      console.error('Error calculating rent vs buy analysis:', err);
      setError('Failed to calculate analysis. Please try again later.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <h1 className="text-3xl font-bold mb-8">Rent vs Buy Analysis</h1>
      
      {/* Analysis Form */}
      <div className="bg-white rounded-lg shadow-md p-6 mb-8">
        <h2 className="text-xl font-semibold mb-4">Compare Renting vs Buying</h2>
        
        <form onSubmit={calculateAnalysis}>
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
                <div className="col-span-3 bg-gray-100 p-4 rounded-lg mb-4">
                  <h3 className="text-lg font-semibold mb-2">Purchase Parameters</h3>
                  <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                    <div>
                      <label htmlFor="downPayment" className="form-label">Down Payment (AED)</label>
                      <input
                        type="number"
                        id="downPayment"
                        name="downPayment"
                        value={analysisParams.downPayment}
                        onChange={handleParamChange}
                        className="form-input"
                        min="0"
                        max={property.price}
                        required
                      />
                      <div className="text-sm text-gray-500 mt-1">
                        {analysisParams.downPayment && property.price
                          ? `${((analysisParams.downPayment / property.price) * 100).toFixed(1)}% of property price`
                          : ''}
                      </div>
                    </div>
                    
                    <div>
                      <label htmlFor="interestRate" className="form-label">Interest Rate (%)</label>
                      <input
                        type="number"
                        id="interestRate"
                        name="interestRate"
                        value={analysisParams.interestRate}
                        onChange={handleParamChange}
                        className="form-input"
                        min="0"
                        max="20"
                        step="0.1"
                        required
                      />
                    </div>
                    
                    <div>
                      <label htmlFor="loanTenureYears" className="form-label">Loan Tenure (Years)</label>
                      <input
                        type="number"
                        id="loanTenureYears"
                        name="loanTenureYears"
                        value={analysisParams.loanTenureYears}
                        onChange={handleParamChange}
                        className="form-input"
                        min="1"
                        max="35"
                        required
                      />
                    </div>
                    
                    <div>
                      <label htmlFor="propertyAppreciationRate" className="form-label">Property Appreciation Rate (%)</label>
                      <input
                        type="number"
                        id="propertyAppreciationRate"
                        name="propertyAppreciationRate"
                        value={analysisParams.propertyAppreciationRate}
                        onChange={handleParamChange}
                        className="form-input"
                        min="-20"
                        max="20"
                        step="0.1"
                        required
                      />
                    </div>
                    
                    <div>
                      <label htmlFor="annualMaintenanceCost" className="form-label">Annual Maintenance Cost (AED)</label>
                      <input
                        type="number"
                        id="annualMaintenanceCost"
                        name="annualMaintenanceCost"
                        value={analysisParams.annualMaintenanceCost}
                        onChange={handleParamChange}
                        className="form-input"
                        min="0"
                        required
                      />
                    </div>
                    
                    <div>
                      <label htmlFor="annualPropertyTax" className="form-label">Annual Property Tax (AED)</label>
                      <input
                        type="number"
                        id="annualPropertyTax"
                        name="annualPropertyTax"
                        value={analysisParams.annualPropertyTax}
                        onChange={handleParamChange}
                        className="form-input"
                        min="0"
                        required
                      />
                    </div>
                  </div>
                </div>
                
                <div className="col-span-3 bg-gray-100 p-4 rounded-lg mb-4">
                  <h3 className="text-lg font-semibold mb-2">Rental Parameters</h3>
                  <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                    <div>
                      <label htmlFor="monthlyRent" className="form-label">Monthly Rent (AED)</label>
                      <input
                        type="number"
                        id="monthlyRent"
                        name="monthlyRent"
                        value={analysisParams.monthlyRent}
                        onChange={handleParamChange}
                        className="form-input"
                        min="0"
                        required
                      />
                    </div>
                    
                    <div>
                      <label htmlFor="annualRentIncreaseRate" className="form-label">Annual Rent Increase Rate (%)</label>
                      <input
                        type="number"
                        id="annualRentIncreaseRate"
                        name="annualRentIncreaseRate"
                        value={analysisParams.annualRentIncreaseRate}
                        onChange={handleParamChange}
                        className="form-input"
                        min="0"
                        max="20"
                        step="0.1"
                        required
                      />
                    </div>
                    
                    <div>
                      <label htmlFor="securityDeposit" className="form-label">Security Deposit (AED)</label>
                      <input
                        type="number"
                        id="securityDeposit"
                        name="securityDeposit"
                        value={analysisParams.securityDeposit}
                        onChange={handleParamChange}
                        className="form-input"
                        min="0"
                        required
                      />
                    </div>
                  </div>
                </div>
                
                <div className="col-span-3 bg-gray-100 p-4 rounded-lg mb-4">
                  <h3 className="text-lg font-semibold mb-2">Investment & Analysis Parameters</h3>
                  <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                    <div>
                      <label htmlFor="investmentReturnRate" className="form-label">Investment Return Rate (%)</label>
                      <input
                        type="number"
                        id="investmentReturnRate"
                        name="investmentReturnRate"
                        value={analysisParams.investmentReturnRate}
                        onChange={handleParamChange}
                        className="form-input"
                        min="0"
                        max="30"
                        step="0.1"
                        required
                      />
                      <div className="text-sm text-gray-500 mt-1">
                        Expected annual return on investments (e.g., stocks, bonds)
                      </div>
                    </div>
                    
                    <div>
                      <label htmlFor="analysisPeriodYears" className="form-label">Analysis Period (Years)</label>
                      <input
                        type="number"
                        id="analysisPeriodYears"
                        name="analysisPeriodYears"
                        value={analysisParams.analysisPeriodYears}
                        onChange={handleParamChange}
                        className="form-input"
                        min="1"
                        max="30"
                        required
                      />
                      <div className="text-sm text-gray-500 mt-1">
                        Time horizon for comparing renting vs buying
                      </div>
                    </div>
                  </div>
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
                    AED {analysisParams.downPayment ? (property.price - analysisParams.downPayment).toLocaleString() : '-'}
                  </span>
                </div>
              </div>
              
              <button 
                type="submit" 
                className="btn btn-primary"
                disabled={loading}
              >
                {loading ? 'Calculating...' : 'Calculate Analysis'}
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
      
      {/* Analysis Results */}
      {analysis && (
        <div className="bg-white rounded-lg shadow-md p-6 mb-8">
          <h2 className="text-2xl font-semibold mb-6">Rent vs Buy Analysis Results</h2>
          
          <div className="grid grid-cols-1 md:grid-cols-3 gap-8 mb-8">
            <div className={`rounded-lg p-6 text-center ${analysis.isBuyingBetter ? 'bg-primary-light' : 'bg-gray-100'}`}>
              <h3 className="text-lg font-semibold mb-2">Buying Net Worth</h3>
              <div className="text-3xl font-bold">AED {analysis.netWorthAfterBuying.toLocaleString(undefined, { maximumFractionDigits: 0 })}</div>
            </div>
            
            <div className={`rounded-lg p-6 text-center ${!analysis.isBuyingBetter ? 'bg-primary-light' : 'bg-gray-100'}`}>
              <h3 className="text-lg font-semibold mb-2">Renting Net Worth</h3>
              <div className="text-3xl font-bold">AED {analysis.netWorthAfterRenting.toLocaleString(undefined, { maximumFractionDigits: 0 })}</div>
            </div>
            
            <div className="bg-gray-100 rounded-lg p-6 text-center">
              <h3 className="text-lg font-semibold mb-2">Break-Even Point</h3>
              <div className="text-3xl font-bold">
                {analysis.breakEvenYears === Number.POSITIVE_INFINITY 
                  ? 'Never' 
                  : `${analysis.breakEvenYears.toFixed(1)} years`}
              </div>
            </div>
          </div>
          
          <div className="bg-gray-50 p-4 rounded-lg mb-6">
            <h3 className="text-xl font-semibold mb-4">Recommendation</h3>
            <p className="text-lg">
              Based on your inputs and a {analysis.analysisPeriodYears}-year analysis period, 
              <strong className="text-primary"> {analysis.isBuyingBetter ? 'buying' : 'renting'} is financially better</strong>.
              {analysis.isBuyingBetter 
                ? ` Buying this property would leave you with approximately AED ${Math.round(analysis.netWorthAfterBuying - analysis.netWorthAfterRenting).toLocaleString()} more in net worth after ${analysis.analysisPeriodYears} years.`
                : ` Renting and investing the difference would leave you with approximately AED ${Math.round(analysis.netWorthAfterRenting - analysis.netWorthAfterBuying).toLocaleString()} more in net worth after ${analysis.analysisPeriodYears} years.`
              }
            </p>
          </div>
          
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-8">
            <div>
              <h3 className="text-lg font-semibold mb-4">Buying Scenario</h3>
              <table className="w-full">
                <tbody>
                  <tr className="border-b">
                    <td className="py-2 text-gray-600">Property Price</td>
                    <td className="py-2 text-right font-medium">AED {property.price.toLocaleString()}</td>
                  </tr>
                  <tr className="border-b">
                    <td className="py-2 text-gray-600">Down Payment</td>
                    <td className="py-2 text-right font-medium">AED {analysis.downPayment.toLocaleString()}</td>
                  </tr>
                  <tr className="border-b">
                    <td className="py-2 text-gray-600">Loan Amount</td>
                    <td className="py-2 text-right font-medium">AED {(property.price - analysis.downPayment).toLocaleString()}</td>
                  </tr>
                  <tr className="border-b">
                    <td className="py-2 text-gray-600">Total Cost of Buying</td>
                    <td className="py-2 text-right font-medium">AED {analysis.totalCostOfBuying.toLocaleString(undefined, { maximumFractionDigits: 0 })}</td>
                  </tr>
                  <tr className="border-b">
                    <td className="py-2 text-gray-600">Property Value After {analysis.analysisPeriodYears} Years</td>
                    <td className="py-2 text-right font-medium">
                      AED {(property.price * Math.pow(1 + analysis.propertyAppreciationRate / 100, analysis.analysisPeriodYears)).toLocaleString(undefined, { maximumFractionDigits: 0 })}
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
            
            <div>
              <h3 className="text-lg font-semibold mb-4">Renting Scenario</h3>
              <table className="w-full">
                <tbody>
                  <tr className="border-b">
                    <td className="py-2 text-gray-600">Initial Monthly Rent</td>
                    <td className="py-2 text-right font-medium">AED {analysis.monthlyRent.toLocaleString()}</td>
                  </tr>
                  <tr className="border-b">
                    <td className="py-2 text-gray-600">Annual Rent Increase</td>
                    <td className="py-2 text-right font-medium">{analysis.annualRentIncreaseRate}%</td>
                  </tr>
                  <tr className="border-b">
                    <td className="py-2 text-gray-600">Total Cost of Renting</td>
                    <td className="py-2 text-right font-medium">AED {analysis.totalCostOfRenting.toLocaleString(undefined, { maximumFractionDigits: 0 })}</td>
                  </tr>
                  <tr className="border-b">
                    <td className="py-2 text-gray-600">Investment Return Rate</td>
                    <td className="py-2 text-right font-medium">{analysis.investmentReturnRate}%</td>
                  </tr>
                  <tr className="border-b">
                    <td className="py-2 text-gray-600">Investment Value After {analysis.analysisPeriodYears} Years</td>
                    <td className="py-2 text-right font-medium">AED {analysis.netWorthAfterRenting.toLocaleString(undefined, { maximumFractionDigits: 0 })}</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
          
          <div className="flex justify-between">
            <Link to={`/properties/${property.id}`} className="text-primary hover:underline">
              Back to Property Details
            </Link>
            <Link to={`/loan-calculator?propertyId=${property.id}`} className="text-primary hover:underline">
              View Loan Calculator
            </Link>
          </div>
        </div>
      )}
    </div>
  );
};

export default RentVsBuy;