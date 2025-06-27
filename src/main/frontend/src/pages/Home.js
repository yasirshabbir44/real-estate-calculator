import React from 'react';
import { Link } from 'react-router-dom';

const Home = () => {
  return (
    <div>
      {/* Hero Section */}
      <section className="bg-gradient-to-r from-primary to-primary-dark text-white py-20">
        <div className="container mx-auto px-4">
          <div className="max-w-3xl">
            <h1 className="text-4xl md:text-5xl font-bold mb-6">Find Your Perfect Property in Dubai</h1>
            <p className="text-xl mb-8">
              Compare properties, calculate mortgage payments, and make informed real estate decisions with our comprehensive tools.
            </p>
            <div className="flex flex-wrap gap-4">
              <Link to="/properties" className="btn bg-white text-primary hover:bg-gray-100">
                Browse Properties
              </Link>
              <Link to="/property-comparison" className="btn bg-transparent border-2 border-white hover:bg-white hover:text-primary">
                Compare Properties
              </Link>
            </div>
          </div>
        </div>
      </section>

      {/* Features Section */}
      <section className="py-16">
        <div className="container mx-auto px-4">
          <h2 className="text-3xl font-bold text-center mb-12">Our Features</h2>
          
          <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
            <div className="card text-center">
              <div className="bg-primary-light rounded-full w-16 h-16 flex items-center justify-center mx-auto mb-4">
                <svg className="w-8 h-8 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6"></path>
                </svg>
              </div>
              <h3 className="text-xl font-semibold mb-2">Property Listings</h3>
              <p className="text-gray-600">
                Browse through our extensive collection of properties in Dubai's most sought-after locations.
              </p>
              <Link to="/properties" className="text-primary font-medium inline-block mt-4 hover:underline">
                View Properties
              </Link>
            </div>
            
            <div className="card text-center">
              <div className="bg-primary-light rounded-full w-16 h-16 flex items-center justify-center mx-auto mb-4">
                <svg className="w-8 h-8 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z"></path>
                </svg>
              </div>
              <h3 className="text-xl font-semibold mb-2">Property Comparison</h3>
              <p className="text-gray-600">
                Compare multiple properties side by side to make the best investment decision.
              </p>
              <Link to="/property-comparison" className="text-primary font-medium inline-block mt-4 hover:underline">
                Compare Now
              </Link>
            </div>
            
            <div className="card text-center">
              <div className="bg-primary-light rounded-full w-16 h-16 flex items-center justify-center mx-auto mb-4">
                <svg className="w-8 h-8 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                </svg>
              </div>
              <h3 className="text-xl font-semibold mb-2">Loan Calculator</h3>
              <p className="text-gray-600">
                Calculate your mortgage payments, interest rates, and loan terms with our easy-to-use calculator.
              </p>
              <Link to="/loan-calculator" className="text-primary font-medium inline-block mt-4 hover:underline">
                Calculate Now
              </Link>
            </div>
          </div>
        </div>
      </section>

      {/* Additional Tools Section */}
      <section className="bg-gray-100 py-16">
        <div className="container mx-auto px-4">
          <h2 className="text-3xl font-bold text-center mb-12">Additional Tools</h2>
          
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            <div className="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition-shadow">
              <h3 className="text-xl font-semibold mb-3">Document Checklist</h3>
              <p className="text-gray-600 mb-4">
                Ensure you have all the necessary documents for your property transaction.
              </p>
              <Link to="/document-checklist" className="text-primary font-medium hover:underline">
                View Checklist →
              </Link>
            </div>
            
            <div className="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition-shadow">
              <h3 className="text-xl font-semibold mb-3">Service Charge Estimate</h3>
              <p className="text-gray-600 mb-4">
                Calculate the estimated service charges for your property.
              </p>
              <Link to="/service-charge-estimate" className="text-primary font-medium hover:underline">
                Calculate Charges →
              </Link>
            </div>
            
            <div className="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition-shadow">
              <h3 className="text-xl font-semibold mb-3">Cost Breakdown</h3>
              <p className="text-gray-600 mb-4">
                Get a detailed breakdown of all costs associated with your property purchase.
              </p>
              <Link to="/cost-breakdown" className="text-primary font-medium hover:underline">
                View Breakdown →
              </Link>
            </div>
          </div>
        </div>
      </section>

      {/* Call to Action */}
      <section className="bg-primary text-white py-16">
        <div className="container mx-auto px-4 text-center">
          <h2 className="text-3xl font-bold mb-6">Ready to Find Your Dream Property?</h2>
          <p className="text-xl mb-8 max-w-2xl mx-auto">
            Start browsing our extensive collection of properties and use our tools to make an informed decision.
          </p>
          <Link to="/properties" className="btn bg-white text-primary hover:bg-gray-100 px-8 py-3 text-lg">
            Get Started
          </Link>
        </div>
      </section>
    </div>
  );
};

export default Home;