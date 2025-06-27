import React from 'react';
import { Link } from 'react-router-dom';

const Navbar = () => {
  return (
    <nav className="bg-primary shadow-md">
      <div className="container mx-auto px-4">
        <div className="flex justify-between items-center py-4">
          <Link to="/" className="text-white text-2xl font-bold">Real Estate App</Link>
          
          <div className="hidden md:flex space-x-6">
            <Link to="/" className="text-white hover:text-secondary-light">Home</Link>
            <Link to="/properties" className="text-white hover:text-secondary-light">Properties</Link>
            <Link to="/property-comparison" className="text-white hover:text-secondary-light">Compare</Link>
            <Link to="/loan-calculator" className="text-white hover:text-secondary-light">Loan Calculator</Link>
            <div className="relative group">
              <button className="text-white hover:text-secondary-light">More</button>
              <div className="absolute left-0 mt-2 w-48 bg-white rounded-md shadow-lg py-1 z-10 hidden group-hover:block">
                <Link to="/document-checklist" className="block px-4 py-2 text-gray-800 hover:bg-primary hover:text-white">Document Checklist</Link>
                <Link to="/service-charge-estimate" className="block px-4 py-2 text-gray-800 hover:bg-primary hover:text-white">Service Charges</Link>
                <Link to="/cost-breakdown" className="block px-4 py-2 text-gray-800 hover:bg-primary hover:text-white">Cost Breakdown</Link>
              </div>
            </div>
          </div>
          
          {/* Mobile menu button */}
          <div className="md:hidden">
            <button className="text-white focus:outline-none">
              <svg className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M4 6h16M4 12h16M4 18h16" />
              </svg>
            </button>
          </div>
        </div>
        
        {/* Mobile menu (hidden by default) */}
        <div className="md:hidden hidden">
          <div className="px-2 pt-2 pb-4 space-y-1">
            <Link to="/" className="block px-3 py-2 text-white hover:bg-primary-dark rounded-md">Home</Link>
            <Link to="/properties" className="block px-3 py-2 text-white hover:bg-primary-dark rounded-md">Properties</Link>
            <Link to="/property-comparison" className="block px-3 py-2 text-white hover:bg-primary-dark rounded-md">Compare</Link>
            <Link to="/loan-calculator" className="block px-3 py-2 text-white hover:bg-primary-dark rounded-md">Loan Calculator</Link>
            <Link to="/document-checklist" className="block px-3 py-2 text-white hover:bg-primary-dark rounded-md">Document Checklist</Link>
            <Link to="/service-charge-estimate" className="block px-3 py-2 text-white hover:bg-primary-dark rounded-md">Service Charges</Link>
            <Link to="/cost-breakdown" className="block px-3 py-2 text-white hover:bg-primary-dark rounded-md">Cost Breakdown</Link>
          </div>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;