import React from 'react';
import { Link } from 'react-router-dom';

const Home = () => {
  return (
    <div className="animate-fadeIn">
      {/* Hero Section */}
      <section className="relative bg-gradient-to-r from-primary to-primary-dark text-white py-24 overflow-hidden">
        {/* Background pattern */}
        <div className="absolute inset-0 opacity-10">
          <svg className="w-full h-full" viewBox="0 0 100 100" preserveAspectRatio="none">
            <defs>
              <pattern id="grid" width="10" height="10" patternUnits="userSpaceOnUse">
                <path d="M 10 0 L 0 0 0 10" fill="none" stroke="currentColor" strokeWidth="0.5" />
              </pattern>
            </defs>
            <rect width="100" height="100" fill="url(#grid)" />
          </svg>
        </div>

        <div className="container mx-auto px-4 relative">
          <div className="max-w-3xl">
            <h1 className="text-4xl md:text-6xl font-bold mb-6 animate-slideInLeft leading-tight">
              Find Your Perfect Property in Dubai
            </h1>
            <p className="text-xl md:text-2xl mb-10 animate-slideInRight opacity-90 leading-relaxed">
              Compare properties, calculate mortgage payments, and make informed real estate decisions with our comprehensive tools.
            </p>
            <div className="flex flex-wrap gap-5">
              <Link to="/properties" className="btn btn-primary bg-white text-primary hover:bg-gray-100 shadow-lg">
                <svg className="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16m14 0h2m-2 0h-5m-9 0H3m2 0h5M9 7h1m-1 4h1m4-4h1m-1 4h1m-5 10v-5a1 1 0 011-1h2a1 1 0 011 1v5m-4 0h4"></path>
                </svg>
                Browse Properties
              </Link>
              <Link to="/property-comparison" className="btn btn-outline text-white border-white hover:text-primary">
                <svg className="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M8 7h12m0 0l-4-4m4 4l-4 4m0 6H4m0 0l4 4m-4-4l4-4"></path>
                </svg>
                Compare Properties
              </Link>
            </div>
          </div>

          {/* Decorative element */}
          <div className="hidden lg:block absolute right-10 top-1/2 transform -translate-y-1/2 w-64 h-64 bg-white bg-opacity-10 rounded-full filter blur-xl"></div>
        </div>
      </section>

      {/* Features Section */}
      <section className="py-20">
        <div className="container mx-auto px-4">
          <h2 className="section-title pb-8">Our Features</h2>

          <div className="grid grid-cols-1 md:grid-cols-3 gap-10 mt-16">
            <div className="card bg-base-100 shadow-xl hover:shadow-card-hover transition-all duration-300 transform hover:-translate-y-2">
              <figure className="px-10 pt-10">
                <div className="bg-primary-light rounded-full w-20 h-20 flex items-center justify-center shadow-lg">
                  <svg className="w-10 h-10 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6"></path>
                  </svg>
                </div>
              </figure>
              <div className="card-body items-center text-center">
                <h3 className="card-title text-2xl font-bold">Property Listings</h3>
                <p className="text-base-content opacity-80">
                  Browse through our extensive collection of properties in Dubai's most sought-after locations.
                </p>
                <div className="card-actions justify-center mt-4">
                  <Link to="/properties" className="btn btn-primary">
                    <span>View Properties</span>
                    <svg className="w-4 h-4 ml-2" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M14 5l7 7m0 0l-7 7m7-7H3"></path>
                    </svg>
                  </Link>
                </div>
              </div>
            </div>

            <div className="card bg-base-100 shadow-xl hover:shadow-card-hover transition-all duration-300 transform hover:-translate-y-2">
              <figure className="px-10 pt-10">
                <div className="bg-primary-light rounded-full w-20 h-20 flex items-center justify-center shadow-lg">
                  <svg className="w-10 h-10 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z"></path>
                  </svg>
                </div>
              </figure>
              <div className="card-body items-center text-center">
                <h3 className="card-title text-2xl font-bold">Property Comparison</h3>
                <p className="text-base-content opacity-80">
                  Compare multiple properties side by side to make the best investment decision.
                </p>
                <div className="card-actions justify-center mt-4">
                  <Link to="/property-comparison" className="btn btn-primary">
                    <span>Compare Now</span>
                    <svg className="w-4 h-4 ml-2" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M14 5l7 7m0 0l-7 7m7-7H3"></path>
                    </svg>
                  </Link>
                </div>
              </div>
            </div>

            <div className="card bg-base-100 shadow-xl hover:shadow-card-hover transition-all duration-300 transform hover:-translate-y-2">
              <figure className="px-10 pt-10">
                <div className="bg-primary-light rounded-full w-20 h-20 flex items-center justify-center shadow-lg">
                  <svg className="w-10 h-10 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                  </svg>
                </div>
              </figure>
              <div className="card-body items-center text-center">
                <h3 className="card-title text-2xl font-bold">Loan Calculator</h3>
                <p className="text-base-content opacity-80">
                  Calculate your mortgage payments, interest rates, and loan terms with our easy-to-use calculator.
                </p>
                <div className="card-actions justify-center mt-4">
                  <Link to="/loan-calculator" className="btn btn-primary">
                    <span>Calculate Now</span>
                    <svg className="w-4 h-4 ml-2" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M14 5l7 7m0 0l-7 7m7-7H3"></path>
                    </svg>
                  </Link>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* Additional Tools Section */}
      <section className="py-20 bg-gradient-to-b from-gray-50 to-secondary">
        <div className="container mx-auto px-4">
          <h2 className="section-title pb-8">Additional Tools</h2>

          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8 mt-16">
            <div className="card hover:shadow-card-hover transition-all duration-300 transform hover:-translate-y-1 flex flex-col h-full">
              <div className="flex items-center mb-4">
                <div className="bg-primary bg-opacity-10 p-3 rounded-lg mr-4">
                  <svg className="w-8 h-8 text-primary" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2"></path>
                  </svg>
                </div>
                <h3 className="text-xl font-bold text-gray-800">Document Checklist</h3>
              </div>
              <p className="text-gray-600 mb-6 flex-grow">
                Ensure you have all the necessary documents for your property transaction. Our comprehensive checklist covers everything you need.
              </p>
              <Link to="/document-checklist" className="text-primary font-medium hover:text-primary-dark transition-all duration-200 flex items-center group">
                <span>View Checklist</span>
                <svg className="w-5 h-5 ml-2 transform group-hover:translate-x-1 transition-transform duration-200" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M14 5l7 7m0 0l-7 7m7-7H3"></path>
                </svg>
              </Link>
            </div>

            <div className="card hover:shadow-card-hover transition-all duration-300 transform hover:-translate-y-1 flex flex-col h-full">
              <div className="flex items-center mb-4">
                <div className="bg-primary bg-opacity-10 p-3 rounded-lg mr-4">
                  <svg className="w-8 h-8 text-primary" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                  </svg>
                </div>
                <h3 className="text-xl font-bold text-gray-800">Service Charge Estimate</h3>
              </div>
              <p className="text-gray-600 mb-6 flex-grow">
                Calculate the estimated service charges for your property. Our tool provides accurate estimates based on property size and location.
              </p>
              <Link to="/service-charge-estimate" className="text-primary font-medium hover:text-primary-dark transition-all duration-200 flex items-center group">
                <span>Calculate Charges</span>
                <svg className="w-5 h-5 ml-2 transform group-hover:translate-x-1 transition-transform duration-200" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M14 5l7 7m0 0l-7 7m7-7H3"></path>
                </svg>
              </Link>
            </div>

            <div className="card hover:shadow-card-hover transition-all duration-300 transform hover:-translate-y-1 flex flex-col h-full">
              <div className="flex items-center mb-4">
                <div className="bg-primary bg-opacity-10 p-3 rounded-lg mr-4">
                  <svg className="w-8 h-8 text-primary" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z"></path>
                  </svg>
                </div>
                <h3 className="text-xl font-bold text-gray-800">Cost Breakdown</h3>
              </div>
              <p className="text-gray-600 mb-6 flex-grow">
                Get a detailed breakdown of all costs associated with your property purchase, including fees, taxes, and other expenses.
              </p>
              <Link to="/cost-breakdown" className="text-primary font-medium hover:text-primary-dark transition-all duration-200 flex items-center group">
                <span>View Breakdown</span>
                <svg className="w-5 h-5 ml-2 transform group-hover:translate-x-1 transition-transform duration-200" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M14 5l7 7m0 0l-7 7m7-7H3"></path>
                </svg>
              </Link>
            </div>
          </div>
        </div>
      </section>

      {/* Call to Action */}
      <section className="relative bg-primary text-white py-20 overflow-hidden">
        {/* Background pattern */}
        <div className="absolute inset-0 opacity-10">
          <svg className="w-full h-full" viewBox="0 0 100 100" preserveAspectRatio="none">
            <defs>
              <pattern id="grid-cta" width="10" height="10" patternUnits="userSpaceOnUse">
                <path d="M 10 0 L 0 0 0 10" fill="none" stroke="currentColor" strokeWidth="0.5" />
              </pattern>
            </defs>
            <rect width="100" height="100" fill="url(#grid-cta)" />
          </svg>
        </div>

        <div className="container mx-auto px-4 text-center relative">
          <div className="card glass max-w-4xl mx-auto">
            <div className="card-body">
              <h2 className="card-title text-4xl font-bold justify-center mb-2">Ready to Find Your Dream Property?</h2>
              <p className="text-xl mb-6 max-w-2xl mx-auto opacity-90">
                Start browsing our extensive collection of properties and use our tools to make an informed decision.
              </p>
              <div className="card-actions justify-center">
                <Link to="/properties" className="btn btn-lg btn-accent gap-2">
                  <span>Get Started</span>
                  <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M14 5l7 7m0 0l-7 7m7-7H3"></path>
                  </svg>
                </Link>
              </div>
            </div>
          </div>

          {/* Decorative elements */}
          <div className="hidden lg:block absolute left-10 top-1/2 transform -translate-y-1/2 w-40 h-40 bg-white bg-opacity-10 rounded-full filter blur-xl"></div>
          <div className="hidden lg:block absolute right-10 bottom-10 w-24 h-24 bg-white bg-opacity-10 rounded-full filter blur-lg"></div>
        </div>
      </section>
    </div>
  );
};

export default Home;
