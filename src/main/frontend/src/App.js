import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navbar from './components/layout/Navbar';
import Footer from './components/layout/Footer';
import Home from './pages/Home';
import PropertyList from './pages/PropertyList';
import PropertyDetail from './pages/PropertyDetail';
import PropertyComparison from './pages/PropertyComparison';
import LoanCalculator from './pages/LoanCalculator';
import DocumentChecklist from './pages/DocumentChecklist';
import ServiceChargeEstimate from './pages/ServiceChargeEstimate';
import CostBreakdown from './pages/CostBreakdown';
import NotFound from './pages/NotFound';

function App() {
  return (
    <Router>
      <div className="flex flex-col min-h-screen">
        <Navbar />
        <main className="flex-grow container mx-auto px-4 py-8">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/properties" element={<PropertyList />} />
            <Route path="/properties/:id" element={<PropertyDetail />} />
            <Route path="/property-comparison" element={<PropertyComparison />} />
            <Route path="/loan-calculator" element={<LoanCalculator />} />
            <Route path="/document-checklist" element={<DocumentChecklist />} />
            <Route path="/service-charge-estimate" element={<ServiceChargeEstimate />} />
            <Route path="/cost-breakdown" element={<CostBreakdown />} />
            <Route path="*" element={<NotFound />} />
          </Routes>
        </main>
        <Footer />
      </div>
    </Router>
  );
}

export default App;